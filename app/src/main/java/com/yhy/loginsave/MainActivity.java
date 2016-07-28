package com.yhy.loginsave;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private SQLiteHelper sqLiteHelper;
    private Cursor cursor;
    private EditText phoneNumber;
    private Button login;
    private ListView userList;
    private int id = 0;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        login = (Button) findViewById(R.id.login);
        userList = (ListView) findViewById(R.id.userList);
        NetControl.setToken("2weqwe5515612zx1czxc");
        sqLiteHelper = new SQLiteHelper(this);
        cursor = sqLiteHelper.select();
        login.setOnClickListener(this);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.item_user,
                cursor,
                new String[]{"UserName","Token"},
                new int[]{R.id.userName,R.id.userToken});
        userList.setAdapter(simpleCursorAdapter);

        phoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(!TextUtils.isEmpty(phoneNumber.getText())){
                        phoneNumber.post(new Runnable() {
                            @Override
                            public void run() {
                                if (phoneNumber != null) {
                                    phoneNumber.setSelection(phoneNumber.getText().length());
                                }
                            }
                        });
                    }
                }
            }
        });
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                cursor.moveToPosition(position);
                id = cursor.getInt(0);
                phoneNumber.setText(cursor.getString(1));
                phoneNumber.setSelection(phoneNumber.getText().length());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                MenuDialogFragment menuDialogFragment  = MenuDialogFragment.newInstance();
                menuDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE,R.style.Theme_yhy);
                menuDialogFragment.show(getSupportFragmentManager(),"menu");
                break;
        }
    }

    /***
     * 增加数据
     * */
    public void addRec(){
        if(phoneNumber.getText().toString().equals("")){
            return;
        }
        sqLiteHelper.insert(phoneNumber.getText().toString(),NetControl.getToken());
        //重新加载数据
        cursor.requery();
        phoneNumber.setText("");
    }
    /**
     * 删除数据
     * **/
    public void deleteRec(){
        sqLiteHelper.delete(id);
        cursor.requery();
        userList.invalidateViews();
        phoneNumber.setText("");
    }
    /***
     * 修改数据
     * */
    public void editRec(){
        if(phoneNumber.getText().toString().equals("")){
            return;
        }
        sqLiteHelper.update(id,phoneNumber.getText().toString(),NetControl.getToken());
        cursor.requery();
        userList.invalidateViews();
        phoneNumber.setText("");
    }

    /***
     * 查询数据
     * **/
    public void queryRec(){
        String  et = phoneNumber.getText().toString();
        String args[] = new String[]{"%"+et+"%"};
        cursor = sqLiteHelper.query(args);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.item_user,
                cursor,
                new String[]{"UserName","Token"},
                new int[]{R.id.userName,R.id.userToken});
        userList.setAdapter(simpleCursorAdapter);
    }

    /***
     * 显示列表
     * */
    public void showList(){
        cursor = sqLiteHelper.select();
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.item_user,
                cursor,
                new String[]{"UserName","Token"},
                new int[]{R.id.userName,R.id.userToken});
        userList.setAdapter(simpleCursorAdapter);
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-exitTime>2000){
            Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }else {
            super.onBackPressed();
        }

    }
}
