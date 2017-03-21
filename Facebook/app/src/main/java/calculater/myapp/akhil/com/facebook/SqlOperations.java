package calculater.myapp.akhil.com.facebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by AKHIL P on 15-03-2017.
 */

public class SqlOperations extends SQLiteOpenHelper
{

    String password;

    public SqlOperations(Context context) {
        super(context, "dbfb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("create table tbl_login(int_login_id_pk integer primary key autoincrement,txt_login_username text,txt_login_password text)");
        }
        catch (SQLiteException e)
        {
            Log.d("Error in create table",e.getLocalizedMessage().toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists tbl_login");
    }
    public boolean insertData(String name,String password)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long result=-1;
        ContentValues mycontent=new ContentValues();
        mycontent.put("txt_login_username",name);
        mycontent.put("txt_login_password",password);
        result=db.insert("tbl_login",null,mycontent);
        if (result==-1) {
            return false;
        }
        else {
            return true;
        }
    }
    public String authenticteUser(String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res =db.rawQuery("select * from tbl_login where txt_login_username='"+name+"'", null);
       // Cursor res=db.query("tbl_login", null, "txt_login_username=?", new String[]{name}, null, null, null);.
        System.out.println(res.getCount());
        if(res.getCount()==0)
        {
            res.close();
            return "not exist";
        }

        if( res != null && res.moveToFirst() ){
            password= res.getString(res.getColumnIndex("txt_login_password"));

        }

        return password;

        //String user=res.getString(0);
//        if(res.getCount()==0)
//        {
//            res=db.rawQuery("select * from tbl_login where txt_login_username='"+name+"'",null);
//        }



    }

    public Cursor getAllStudentDetails()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res =db.rawQuery("select * from tbl_login", null);
        return  res;
    }
}
