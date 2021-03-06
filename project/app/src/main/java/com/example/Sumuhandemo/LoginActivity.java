package com.example.Sumuhandemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Sumuhandemo.utils.HttpUtils;
import com.example.Sumuhandemo.utils.AnalysisUtils;
import com.example.Sumuhandemo.utils.MD5Utils;
import com.example.Sumuhandemo.bean.User_Info;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity{
    private TextView tv_main_title;//标题
    private TextView tv_back,tv_register,tv_find_psw,tv_hint,tv_question;//返回键,显示的注册，找回密码
    private Button btn_login;//登录按钮
    private String userName,psw,spPsw;//获取的用户名，密码，加密密码
    private EditText et_user_name,et_psw;//编辑框
    private MyHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mHandler=new MyHandler(this);
        init();
    }
    //获取界面控件
    private void init() {
        //从main_title_bar中获取的id
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        tv_back=findViewById(R.id.tv_back);
        tv_question=findViewById(R.id.tv_question);
        tv_question.setVisibility(View.INVISIBLE);
        //从activity_login.xml中获取的
        tv_register=findViewById(R.id.tv_register);
        tv_hint=findViewById(R.id.tv_hint);
        btn_login=findViewById(R.id.btn_login);
        et_user_name=findViewById(R.id.et_user_name);
        et_psw=findViewById(R.id.et_psw);
        //返回键的点击事件
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录界面销毁
                LoginActivity.this.finish();
            }
        });
        //立即注册控件的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //为了跳转到注册界面，并实现注册功能
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        //登录按钮的点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始登录，获取用户名和密码 getText().toString().trim();
                userName=et_user_name.getText().toString().trim();
                psw=et_psw.getText().toString().trim();
                // TextUtils.isEmpty
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                    // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                }
                else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Message msg = Message.obtain();
                                msg.obj = new User_Info(userName,"",psw);
                                mHandler.handleMessage(msg);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }

            }
        });
    }

    /**
     * 注册成功的数据返回至此
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 数据
     */
    @Override
    //显示数据， onActivityResult
    //startActivityForResult(intent, 1); 从注册界面中获取数据
    //int requestCode , int resultCode , Intent data
    // LoginActivity -> startActivityForResult -> onActivityResult();
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //是获取注册界面回传过来的用户名
            // getExtra().getString("***");
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                //设置用户名到 et_user_name 控件
                et_user_name.setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
                et_user_name.setSelection(userName.length());
            }
        }
    }

    private static class MyHandler extends Handler {
        WeakReference<AppCompatActivity> reference;

        public MyHandler(AppCompatActivity activity) {
            reference = new WeakReference<>(activity);
        }
        public void handleMessage(Message msg) {
            LoginActivity activity = (LoginActivity) reference.get();
            User_Info ui=(User_Info)msg.obj;
            Map<String,String> mp=new HashMap<String,String>();
            mp.put("name",ui.Username);
            mp.put("password",MD5Utils.md5(ui.Password));
            String sri=HttpUtils.sendGetRequest(mp,"UTF-8","/api/user/login");//
            if(sri!="Failed"){
                try {
                    JSONObject jo = new JSONObject(sri);
                    String MSG=jo.get("msg").toString();
                    if(MSG.equals("Success!")){
                        //保存登录状态，在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
                        AnalysisUtils.setLoginStatus(activity.getApplicationContext(), ui.Username);
                        //登录成功后关闭此页面进入主页
                        Intent data=new Intent(activity, MainActivity.class);
                        //datad.putExtra( ); name , value ;
                        data.putExtra("isLogin",true);
                        data.putExtra("loginUserName",ui.Username);
                        //RESULT_OK为Activity系统常量，状态码为-1
                        // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                        activity.setResult(RESULT_OK,data);
                        //销毁登录界面
                        activity.finish();
                        //跳转到主界面，登录成功的状态传递到 MainActivity 中
                        activity.startActivity(data);
                        return;

                    }
                    else if(MSG.equals("User not found!")){
                        activity.tv_hint.setText("用户名无效");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    Thread.sleep(3000);
                                    activity.tv_hint.setText("");
                                } catch (Exception e) {
                                }
                            }
                        }).start();
                    }
                    else if(MSG.equals("Wrong Password!")){
                        activity.tv_hint.setText("用户名或密码错误");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    Thread.sleep(3000);
                                    activity.tv_hint.setText("");
                                } catch (Exception e) {
                                }
                            }
                        }).start();
                    }
                } catch (JSONException e) {
                }
            }
            else{
                activity.tv_hint.setText("网络超时，请稍后重试");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(3000);
                            activity.tv_hint.setText("");
                        } catch (Exception e) {
                        }
                    }
                }).start();
            }
        }
    }
}
