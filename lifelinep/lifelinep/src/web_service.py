import cmd
import os
from flask import *
from werkzeug.utils import secure_filename
from src.dbconnection import *
app=Flask(__name__)
con=pymysql.connect(host="localhost",port=3306,user="root",password="",db='lifeline')
cmd=con.cursor()
@app.route('/login',methods=['GET','POST'])
def login():
    username=request.form['usernm']
    password=request.form['pass']
    qry= "SELECT * FROM `login` WHERE `username`=%s AND `password`=%s"
    value=(username, password)
    res=select(qry,value)
    if res is None:
        return jsonify({'result': "invalid"})
    else:
        id = str(res[0])
        type = str(res[3])
        return jsonify({'result': id +"#"+type})

@app.route('/registration',methods=['GET','POST'])
def registration():
    try:
        fname=request.form["fname"]
        lname=request.form["lname"]
        place=request.form["place"]
        post=request.form["post"]
        pin=request.form["pin"]
        district=request.form["dist"]
        phone=request.form["phone"]
        email=request.form["email"]
        usrnm=request.form["username"]
        password=request.form["password"]
        qry = "insert into login VALUES (NULL ,%s,%s,'user')"
        values = (usrnm, password)
        id = insert(qry, values)
        qry = "insert into user VALUES (NULL ,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        values = (str(id), fname, lname, place, post, pin, district, phone, email)
        insert(qry, values)
        return jsonify({'task': "success"})
    except Exception as e:
        return ({'task': 'invalid'})
@app.route('/add_feedback',methods=['GET','POST'])
def add_feedback():
    uid=request.form['userid']
    feed=request.form['feedback']
    qry="insert into feedback VALUES (NULL ,%s,%s,CURRENT_DATE )"
    values=(str(uid),feed)
    insert(qry,values)
    return jsonify({'result':"success"})

@app.route('/view_items',methods=['POST'])
def view_items():
    cmd.execute("select distinct(item_type) from item")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)

@app.route('/view_item',methods=['POST'])
def view_item():
    cmd.execute("select * from item")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)

@app.route('/view_items1',methods=['POST'])
def view_items1():
    type=request.form['type']
    cmd.execute("select * from item where item_type='"+str(type)+"'")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)

@app.route('/request_item',methods=['GET','POST'])
def request_item():
    uid=request.form['uid']
    did=request.form['did']
    item_id=request.form['item_id']
    description=request.form['desciption']
    qry = "insert into request VALUES (NULL,%s,%s,%s,%s,'pending',curdate())"
    values=(str(uid),item_id,description,str(did))
    insert(qry,values)
    return jsonify({'task': "success"})

@app.route('/update_status',methods=['GET','POST'])
def update_status():

    status=request.form['status']
    wid=request.form['wid']
    print(wid)
    qry="update `assign` set `status`=%s where `request_id`=%s"
    values=(status,str(wid))
    insert(qry,values)
    return jsonify({'task': "success"})

@app.route('/view_work',methods=['GET','POST'])
def view_work():
    aid=request.form['lid']
    print(aid)
    cmd.execute("SELECT assign.*,request.item_id,request.date,request.description,item.item_name,donor1.first_name AS donorf,donor1.last_name AS donorl,user.first_name AS user1,user.last_name AS user2 FROM `assign` JOIN request ON assign.request_id=request.request_id JOIN USER ON request.login_id=user.login_id JOIN item ON item.item_id=request.item_id JOIN donor1 ON request.donor_id=donor1.login_id where assign.agent_id='"+aid+"' and assign.status='pending'")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)


if __name__=='__main__':
    app.run(host='0.0.0.0',port=5000)
