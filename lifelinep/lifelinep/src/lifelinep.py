from flask import *
from src.dbconnection import *
import os
from werkzeug.utils import secure_filename

app = Flask(__name__)
path=r"../src/static/files"
app.secret_key="hiii"



import functools
def login_required(func):
    @functools.wraps(func)
    def secure_function():
        if "lid" not in session:
            return redirect ("/")
        return func()
    return secure_function

@app.route("/logout")
# @login_required

def logout():
    session.clear()
    return render_template("login.html")

#
# @app.route('/')
# def login():
#     return render_template('index.html')
@app.route('/')
def index1():
    return render_template('login.html')
@app.route('/adminindex')
def adminindex():
    return render_template('adminindex.html')
@app.route('/donorindex')
def donorindex():
    return render_template('donorindex.html')

@app.route('/login1',methods=['GET','POST'])
def login1():
    username=request.form['usrname']
    password=request.form['pass']
    qry="select * from `login` WHERE `username`=%s AND `password`=%s"
    values=(username,password)
    result=select(qry,values)
    if (result == None):
        return '''<script>alert("invalid User");window.location="/"</script>'''
    elif (result[3] == "admin"):
        session['lid'] = result[0]
        return '''<script>window.location="/adminindex"</script>'''
    elif (result[3] == "donor"):
        session['lid'] = result[0]
        return '''<script>window.location="/donorindex"</script>'''
    else:
        return '''<script>alert("invalid Username or password");window.location="/"</script>'''

@app.route('/adminhome')
# @login_required
def adminhome():
    return render_template('adminhome.html')

@app.route('/donorhome')
# @login_required
def donorhome():
    return render_template('donorhome.html')

@app.route('/view_delivery_agent')
@login_required
def view_delivery_agent():
    qry="select * from `agent`"
    res=selectall(qry)
    return render_template('view_delivery_agent.html',data=res)

@app.route('/view_delivery_agent1')
@login_required
def view_delivery_agent1():
    qry="select * from `agent`"
    res=selectall(qry)
    return render_template('view_delivery_agent1.html',data=res)


@app.route('/view_user')
@login_required
def view_user():
    qry="select * from `user`"
    result=selectall(qry)
    return render_template('view_user.html',data=result)

@app.route('/blockunblock')
@login_required
def blockunblock():
    qry="SELECT `user`.*,`login`.`user_type` FROM `login` JOIN `user` ON `user`.`login_id`=`login`.`Login_id`"
    res=selectall(qry)
    return render_template('blockunblockusers.html',val=res)

@app.route('/block')
def block():
    id=request.args.get('id')
    qry="UPDATE `login` SET `user_type`='block' WHERE `Login_id`=%s"
    val=str(id)
    insert(qry,val)
    return  '''<script>alert("blocked....!!!!");window.location="/blockunblock"</script>'''
@app.route('/unblock')
def unblock():
    id=request.args.get('id')
    qry="UPDATE `login` SET `user_type`='user' WHERE `Login_id`=%s"
    val=str(id)
    insert(qry,val)
    return  '''<script>alert("unblocked....!!!!");window.location="/blockunblock"</script>'''

@app.route('/blockunblockdonor',methods=['post'])
@login_required
def blockunblockdonor():
    qry="SELECT `donor1`.*,`login`.`user_type` FROM `login` JOIN `donor1` ON `donor1`.`login_id`=`login`.`Login_id`"
    res=selectall(qry)
    return render_template('blockunblockdoner.html',val=res)

@app.route('/blockdonor')
def blockdonor():
    id=request.args.get('id')
    qry="UPDATE `login` SET `user_type`='block' WHERE `Login_id`=%s"
    val=str(id)
    insert(qry,val)
    return  '''<script>alert("blocked....!!!!");window.location="/blockunblock"</script>'''
@app.route('/unblockdonor')
def unblockdonor():
    id=request.args.get('id')
    qry="UPDATE `login` SET `user_type`='donor' WHERE `Login_id`=%s"
    val=str(id)
    insert(qry,val)
    return  '''<script>alert("unblocked....!!!!");window.location="/blockunblock"</script>'''




@app.route('/view_feedback')
@login_required
def view_feedback():
    qry="select `feedback`.`date`,`login`.`username`,`feedback`.`feedback` from login inner join feedback where login.Login_id=feedback.login_id"
    result=selectall(qry)
    return render_template('view_feedback.html',data=result)
@app.route('/view_report')
@login_required
def view_report():
    qry="select `report`.`date`,`donor1`.`first_name`,`donor1`.`last_name`,`report`.`report` from donor1 inner join `report` on `donor1`.`login_id`=`report`.`login_id`"
    result=selectall(qry)
    return render_template('view_report.html',data=result)
@app.route('/add_donor')
@login_required
def add_donor():
    return render_template('add_donor.html')

@app.route('/add_donor1',methods=['GET','POST'])
def add_donor1():
    try:
        fname=request.form['textfield']
        lname=request.form['textfield2']
        gender=request.form['RadioGroup1']
        dob=request.form['dat']
        place=request.form['textfield3']
        post=request.form['textfield4']
        pin=request.form['textfield5']
        email=request.form['textfield6']
        phone=request.form['textfield7']
        username=request.form['textfield8']
        password=request.form['textfield9']
        qry = "insert into `login` VALUES (NULL ,%s,%s,'donor')"
        values = (username, password)
        lid = insert(qry, values)
        qry = "insert into `donor1` VALUES (NULL ,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        values = (str(lid), fname, lname, gender, dob, place, post, pin, email, phone)
        insert(qry, values)
        return '''<script>alert("succesfully registered donor");window.location="/adminindex"</script>'''
    except Exception as e:
        return  '''<script>alert("Username already existing!!");window.location='/add_donor'</script>'''


@app.route('/view_donor')
@login_required
def view_donor():
    qry="select * from `donor1`"
    res=selectall(qry)
    return render_template('viewdonor.html',data=res)

@app.route('/edit_donor')
@login_required
def edit_donor():
    id=request.args.get('id')
    session['id']=id
    qry="select * from login where login_id=%s"
    values=(str(id))
    res=select(qry,values)
    qry="select * from `donor1` where `login_id`=%s"
    values=(str(id))
    result=select(qry,values)
    return render_template('edit_donor.html',data=result,data1=res)

@app.route('/edit_donor1',methods=['GET','POST'])

def edit_donor1():
    try:
        # id = request.args.get('id')
        fname = request.form['textfield']
        lname = request.form['textfield2']
        gender = request.form['RadioGroup1']
        dob = request.form['dat']
        place = request.form['textfield3']
        post = request.form['textfield4']
        pin = request.form['textfield5']
        email = request.form['textfield6']
        phone = request.form['textfield7']
        username = request.form['textfield8']
        password = request.form['textfield9']
        qry="update `login` set `username`=%s,`password`=%s where `login_id`=%s"
        values=(username,password,str(session['id']))
        insert(qry,values)
        qry="update `donor1` set `first_name`=%s,`last_name`=%s,`gender`=%s,`dob`=%s,`place`=%s,`post`=%s,`pin`=%s,`email`=%s,`phone`=%s where `login_id`=%s"
        values=(fname,lname,gender,dob,place,post,pin,email,phone,str(session['id']))
        print(values)
        insert(qry,values)
        return '''<script>alert("Succesfully updated");window.location="/adminindex"</script>'''
    except Exception as e:
        return '''<script>alert("Username already existing!!");window.location='/edit_donor'</script>'''
@app.route("/delete_donor")
def delete_donor():
    id=request.args.get('id')
    qry="delete from `donor1` where `login_id`=%s"
    values=(str(id))
    insert(qry,values)
    qry="delete from login where login_id=%s"
    values=(str(id))
    insert(qry,values)
    return '''<script>alert("succesfully deleted");window.location="/adminindex"</script>'''

# ................donar home ............... #

@app.route('/donor_view_delivery_agent')
@login_required
def donor_view_delivery_agent():
    qry="select * from `agent`"
    res=selectall(qry)
    return render_template('donor_view_delivery_agent.html',data=res)

@app.route('/add_delivery_agent')
@login_required
def add_delivery_agent():
    return render_template('add_delivery_agent.html')

@app.route('/add_delivery_agent1',methods=['GET','POST'])
def add_delivery_agent1():
    try:
        fname=request.form['textfield']
        lname=request.form['textfield2']
        gender=request.form['RadioGroup1']
        place=request.form['textfield3']
        email=request.form['textfield4']
        phone=request.form['textfield5']
        username=request.form['textfield6']
        password=request.form['textfield7']
        qry = "insert into login values(NULL ,%s,%s,'agent')"
        values = (username, password)
        lid = insert(qry, values)
        print(lid)
        qry = "insert into agent values(NULL,%s,%s,%s,%s,%s,%s,%s)"
        values = (str(lid), fname, lname, gender, place, email, phone)
        insert(qry, values)
        return '''<script>alert("Succesfully added delivery agent");window.location="/donor_view_delivery_agent"</script>'''
    except Exception as e:
        return '''<script>alert("Username already exist!!");window.location="/add_delivery_agent"</script>'''


@app.route('/edit_delivery_agent')
@login_required
def edit_delivery_agent():
    id = request.args.get('eid')
    session['eid'] = id
    qry = "select * from login where login_id=%s"
    values = (str(session['eid']))
    res = select(qry, values)
    print(res)
    qry = "select * from `agent` where `login_id`=%s"
    values = (str(session['eid']))
    result = select(qry, values)
    print(result)
    return render_template('edit_delivery_agent.html',data=result,data1=res)

@app.route('/edit_delivery_agent1',methods=['GET','POST'])
def edit_delivery_agent1():
    try:
        fname = request.form["textfield"]
        lname = request.form["textfield2"]
        gend = request.form["ra"]
        place = request.form["textfield3"]
        email = request.form["textfield4"]
        phone = request.form["textfield5"]
        username = request.form['textfield6']
        password = request.form['textfield7']
        qry = "update `login` set `username`=%s,`password`=%s where `login_id`=%s"
        values = (username, password, str(session['eid']))
        insert(qry, values)
        qry="update `agent` set `first_name`=%s,`last_name`=%s,`gender`=%s,`place`=%s,`email`=%s,`phone`=%s where `login_id`=%s"
        values=(fname,lname,gend,place,email,phone,str(session['eid']))
        insert(qry,values)
        return '''<script>alert("succesfully updated");window.location="/donorindex"</script>'''
    except Exception as e:
        return '''<script>alert("Username already exist!!");window.location="/edit_delivery_agent"</script>'''


@app.route('/delete_agent')
def delete_agent():
    id=request.args.get('id')
    qry="delete from `agent` WHERE `login_id`=%s"
    val=(str(id))
    insert(qry,val)
    qry = "delete from login where login_id=%s"
    values = (str(id))
    insert(qry, values)
    return '''<script>alert("succesfully deleted");window.location="/donorindex"</script>'''

@app.route('/add_item_details')
@login_required
def add_item_details():
    return render_template("add_item_details.html")

@app.route('/add_item_details1',methods=['GET','POST'])
def add_item_details1():
    it_type=request.form["select"]
    item_name=request.form["textfield"]
    quantity=request.form["textfield2"]
    qry="insert into `item` VALUES (NULL ,%s,%s,%s,%s)"
    values=(str(session['lid']),it_type,item_name,quantity)
    insert(qry,values)
    return '''<script>alert("succesfully added item details");window.location="/donorindex"</script>'''

@app.route('/view_items')
@login_required
def view_items():
    qry="select * from `item` where login_id='"+str(session['lid'])+"'"
    result=selectall(qry)
    return render_template("view_items.html",data=result)

@app.route('/edit_item_details')
@login_required
def edit_item_details():
    id=request.args.get('id')
    session['id']=id
    qry="select * from `item` where `item_id`=%s and `login_id`=%s"
    values=(str(id),str(session['lid']))
    res=select(qry,values)
    return render_template('edit_item_details.html',data=res)

@app.route('/edit_item_details1',methods=['GET','POST'])
def edit_item_details1():
    it_name = request.form["itemm"]
    quan = request.form["quan"]
    qry="update `item` set `item_name`=%s,`quantity`=%s where `item_id`=%s and `login_id`=%s"
    values=(it_name,quan,str(session['id']),str(session['lid']))
    insert(qry,values)
    return '''<script>alert("succesfully updated item details");window.location="/donorindex"</script>'''

@app.route('/delete_item')
def delete_item():
    id=request.args.get('id')
    qry="delete from `item` WHERE `item_id`=%s AND `login_id`=%s"
    val=(str(id),str(session['lid']))
    insert(qry,val)
    return '''<script>alert("succesfully deleted item");window.location="/donorindex"</script>'''

@app.route('/view_request')
@login_required
def view_request():
    return render_template("request.html")


@app.route('/view_request1',methods=['GET','POST'])
@login_required
def view_request1():
    item_type=request.form["select"]
    qry="SELECT item.item_name,user.first_name,user.last_name,item.quantity,request.request_id FROM request INNER JOIN item ON request.item_id=item.item_id INNER JOIN `user` ON request.login_id=user.login_id WHERE item.item_type=%s AND request.status='pending'"
    values=(item_type)
    res=selecte(qry,values)
    return render_template("request.html",data=res)

@app.route('/assign_agent')
@login_required
def assign_agent():

    session['req_id']=request.args.get('id')
    qry="select * from agent"
    res=selectall(qry)

    return render_template("assign.html",data=res)

@app.route('/assign_agent1',methods=['GET','POST'])
def assign_agent1():
    agent=request.form["select"]
    qry="insert into `assign` VALUES (NULL ,%s,%s,%s,'pending')"
    values=(str(session['lid']),agent,str(session['req_id']))
    insert(qry,values)
    qry="update request set status='assigned' where request_id=%s"
    values=(str(session['req_id']))
    insert(qry,values)
    return '''<script>alert("succesfully assigned agent");window.location="/donorindex"</script>'''
@app.route('/view_status')
@login_required
def view_status():
    qry="SELECT agent.first_name,agent.last_name,item.item_name,user.first_name,user.last_name,assign.status FROM request JOIN assign ON assign.request_id=request.request_id JOIN item ON item.item_id=request.item_id JOIN agent ON agent.login_id=assign.agent_id JOIN USER ON request.login_id=user.login_id"
    res=selectall(qry)
    return render_template("view_status.html",data=res)

@app.route('/add_report')
@login_required
def add_report():
    qry = "SELECT agent.first_name,agent.last_name,item.item_name,user.first_name,user.last_name,assign.assign_id,agent.agent_id,item.item_id,user.user_id FROM request JOIN `user` ON request.login_id=user.login_id JOIN assign ON assign.request_id=request.request_id JOIN item ON item.item_id=request.item_id JOIN agent ON agent.login_id=assign.agent_id WHERE assign.assign_id NOT IN(SELECT assign_id FROM report) and assign.login_id=%s AND `assign`.`status`='completed' "
    value=(str(session['lid']))
    res = selecte(qry,value)
    return render_template("add_report.html",data=res)


@app.route('/report')
@login_required
def report():
    id = request.args.get('id')
    session['id'] = id
    aid=request.args.get('aid')
    session['aid']=aid
    itemid=request.args.get('itemid')
    session['itemid']=itemid
    userid=request.args.get('userid')
    session['userid']=userid
    return render_template("report.html")

@app.route('/report1',methods=['GET','POST'])
def report1():
    file = request.files['fileField']
    var = secure_filename(file.filename)
    file.save(os.path.join(path, var))
    qry = "insert into `report` VALUES (NULL ,%s,CURRENT_DATE,%s,%s,%s,%s,%s)"
    values = (str(session['lid']),str(session['id']),var,str(session['aid']),str(session['itemid']),str(session['userid']))
    insert(qry, values)
    return '''<script>alert("succesfully added");window.location="/add_report"</script>'''

@app.route('/donor_view_report')
@login_required
def donor_view_report():
    qry="select agent.first_name,agent.last_name,item.item_name,user.first_name,user.last_name,report.report,report.report_id from report join `user` on report.user_id=user.user_id join assign on report.assign_id=assign.assign_id join item on item.item_id=report.item_id join agent on agent.agent_id=report.agent_id where report.login_id=%s"
    values=(str(session['lid']))
    result=selecte(qry,values)
    return render_template("donor_view_report.html",data=result)

@app.route('/edit_report')
@login_required
def edit_report():
    id=request.args.get('id')
    session['id']=id
    return render_template("edit_report.html")

@app.route('/edit_report1',methods=['GET','POST'])
def edit_report1():
    file = request.files['fileField']
    var = secure_filename(file.filename)
    file.save(os.path.join(path, var))
    qry = "update report set report=%s where report_id=%s"
    values = (var,str(session['id']))
    insert(qry, values)
    return '''<script>alert("succesfully edited");window.location="/donorindex"</script>'''

@app.route('/delete_report')
def delete_report():
    id = request.args.get('id')
    qry = "delete from report where report_id=%s"
    values = (str(id))
    insert(qry, values)
    return '''<script>alert("succesfully deleted");window.location="/donorindex"</script>'''
if __name__ == '__main__':
    app.run(debug=True)
