package calculater.myapp.akhil.com.facebook;

import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button create,login;
    EditText user,pass;
    TextView error;
    String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custometitlebar);
        View view =getSupportActionBar().getCustomView();

        final SqlOperations db=new SqlOperations(this);
        create=(Button)findViewById(R.id.create);
        login=(Button)findViewById(R.id.log);

        user=(EditText)findViewById(R.id.et_1);
        pass=(EditText)findViewById(R.id.et_2);
        password=pass.toString();
        System.out.println(password);
        error=(TextView)findViewById(R.id.error);
        create.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                if (user.getText().toString().equals("")&&pass.getText().toString().equals(""))
                {
                    Cursor c=db.getAllStudentDetails();
                    if(c.getCount()==0)
                    {
                        showAllData("Error","No Data in Database");
                        return;
                    }
                    StringBuffer sb=new StringBuffer();
                    while (c.moveToNext())
                    {
                        sb.append("ID:"+c.getString(0)+"\n");
                        sb.append("name:"+c.getString(1)+"\n");
                        sb.append("age:"+c.getString(2)+"\n");
                    }
                    showAllData("Success",sb.toString());
                }
                else{
                    boolean inserted=db.insertData(user.getText().toString(),pass.getText().toString());
                    if(inserted=true)
                    {
                        Toast.makeText(getApplicationContext(),"data inserted",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernames=user.getText().toString();
                String passwords=user.getText().toString();
                System.out.println(usernames);
                String dbpass = db.authenticteUser(usernames);
                System.out.println(dbpass);
                if ( dbpass.equals("")) {
                    Toast.makeText(getApplicationContext(), "email error", Toast.LENGTH_SHORT).show();
                }
                 else if ( dbpass.equals(passwords)) {
                        Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "password error", Toast.LENGTH_SHORT).show();



            }
        });


    }
    public void showAllData(String tittle,String msg)
    {
        AlertDialog.Builder alertmsg=new AlertDialog.Builder(this);
        alertmsg.setCancelable(true);
        alertmsg.setTitle(tittle);
        alertmsg.setMessage(msg);
        alertmsg.show();
    }


}
