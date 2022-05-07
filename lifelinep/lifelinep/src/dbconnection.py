import pymysql
# passwd="root"
def select(qry,values):
    con=pymysql.connect(host="localhost",port=3306,user="root",password='',db='lifeline')
    cmd=con.cursor()
    cmd.execute(qry,values)
    res=cmd.fetchone()
    con.close()
    return res

def insert(qry,values):
    con = pymysql.connect(host="localhost", port=3306, user="root", password='', db='lifeline')
    cmd = con.cursor()
    cmd.execute(qry,values)
    id=cmd.lastrowid
    con.commit()
    return id

def selectall(qry):
    con=pymysql.connect(host="localhost",port=3306,user="root",password='',db='lifeline')
    cmd=con.cursor()
    cmd.execute(qry)
    res=cmd.fetchall()
    con.close()
    return res

def selecte(qry,values):
    con=pymysql.connect(host="localhost",port=3306,user="root",password='',db='lifeline')
    cmd=con.cursor()
    cmd.execute(qry,values)
    res=cmd.fetchall()
    con.close()
    return res