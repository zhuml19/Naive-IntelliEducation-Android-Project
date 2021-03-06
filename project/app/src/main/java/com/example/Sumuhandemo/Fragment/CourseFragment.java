package com.example.Sumuhandemo.Fragment;


import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import com.example.Sumuhandemo.bean.Item;

import com.example.Sumuhandemo.R;
import com.example.Sumuhandemo.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import com.alibaba.fastjson.*;


public class CourseFragment extends Fragment implements View.OnClickListener{

    private List<Item> SubjectList;
    private List<Item> toadd_SubjectList=new ArrayList<>();
    private List<Item> total_SubjectList=new ArrayList<>();
    private TextView plus_btn;
    private RecyclerView recyclerView;
    private RecyclerView currentView;
    private RecyclerView toadd_View;
    private View view;
    private Recycler1 radapter;
    private Recycler adapter;
    private Recycler toadd_adapter;
    private ViewPager SubViewPager;
    private ImageView refresh;
    private List<Fragment> SubFragments=new ArrayList<>();

    public CourseFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SubFragments.get(position);
        }

        public long getItemId(int position){
            return SubjectList.get(position).getName().hashCode();
        }

        @Override
        public int getCount() {
            return SubFragments.size();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_course, container, false);
        plus_btn=(TextView)view.findViewById(R.id.plus_btn);
        SubViewPager = (ViewPager) view.findViewById(R.id.subpage);

        initSubject();
        SubViewPager.setOffscreenPageLimit(SubFragments.size());
        SubViewPager.setAdapter(new MyFragmentAdapter(getChildFragmentManager()));
        SubViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //position??????????????????????????????????????????????????????????????????????????????index??????????????????????????????????????????
                radapter.mposition=position;
                radapter.notifyDataSetChanged();
            }
        });
        radapter = new Recycler1(SubjectList);
        recyclerView = (RecyclerView) view.findViewById(R.id.viewpage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(radapter);


        refresh=(ImageView) view.findViewById(R.id.refresh);
        refresh.setAlpha(0.5f);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubViewPager.setAdapter(new MyFragmentAdapter(getChildFragmentManager()));
                SubViewPager.setCurrentItem(radapter.mposition);
            }
        });

        setListener(view);
        return view;
    }


    private void initSubject(){
        total_SubjectList.add(new Item("??????"));
        total_SubjectList.add(new Item("??????"));
        total_SubjectList.add(new Item("??????"));
        total_SubjectList.add(new Item("??????"));
        total_SubjectList.add(new Item("??????"));
        total_SubjectList.add(new Item("??????"));
        total_SubjectList.add(new Item("??????"));
        total_SubjectList.add(new Item("??????"));
        total_SubjectList.add(new Item("??????"));

        SharedPreferences sp=getActivity().getSharedPreferences("SubjectInfo", MODE_PRIVATE);
        SubjectList=new ArrayList<>();
        SharedPreferences.Editor editor=sp.edit();
        if(sp.getBoolean("FirstTime?",true)){
            //????????????????????????????????????
            SubjectList.add(new Item("??????"));
            SubjectList.add(new Item("??????"));
            SubjectList.add(new Item("??????"));
            editor.putBoolean("??????",true);
            editor.putBoolean("??????",true);
            editor.putBoolean("??????",true);
            toadd_SubjectList.add(new Item("??????"));
            toadd_SubjectList.add(new Item("??????"));
            toadd_SubjectList.add(new Item("??????"));
            toadd_SubjectList.add(new Item("??????"));
            toadd_SubjectList.add(new Item("??????"));
            toadd_SubjectList.add(new Item("??????"));
            editor.putBoolean("FirstTime?",false);
            editor.commit();
        }else {
            if (sp.getBoolean("??????", false)) {
                SubjectList.add(new Item("??????"));
            } else {
                toadd_SubjectList.add(new Item("??????"));
            }
            if (sp.getBoolean("??????", false)) {
                SubjectList.add(new Item("??????"));
            } else {
                toadd_SubjectList.add(new Item("??????"));
            }
            if (sp.getBoolean("??????", false)) {
                SubjectList.add(new Item("??????"));
            } else {
                toadd_SubjectList.add(new Item("??????"));
            }
            if (sp.getBoolean("??????", false)) {
                SubjectList.add(new Item("??????"));
            } else {
                toadd_SubjectList.add(new Item("??????"));
            }
            if (sp.getBoolean("??????", false)) {
                SubjectList.add(new Item("??????"));
            } else {
                toadd_SubjectList.add(new Item("??????"));
            }
            if (sp.getBoolean("??????", false)) {
                SubjectList.add(new Item("??????"));
            } else {
                toadd_SubjectList.add(new Item("??????"));
            }
            if (sp.getBoolean("??????", false)) {
                SubjectList.add(new Item("??????"));
            } else {
                toadd_SubjectList.add(new Item("??????"));
            }
            if (sp.getBoolean("??????", false)) {
                SubjectList.add(new Item("??????"));
            } else {
                toadd_SubjectList.add(new Item("??????"));
            }
            if (sp.getBoolean("??????", false)) {
                SubjectList.add(new Item("??????"));
            } else {
                toadd_SubjectList.add(new Item("??????"));
            }
        }

        SubFragments=new ArrayList<>();
        Iterator<Item> iterator = total_SubjectList.iterator();
        while (iterator.hasNext()){
            Item next = iterator.next();
            String name = next.getName();
            if (isIn(name,SubjectList)){
                SubFragments.add(new SubjectFragment(name));
            }
        }
    }


    boolean isIn(String sub,List<Item> lst){
        Iterator<Item> iterator = lst.iterator();
        while (iterator.hasNext()){
            Item next = iterator.next();
            String name = next.getName();
            if (name.equals(sub)){
                return true;
            }
        }
        return false;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plus_btn:
                showBottomDialog();
                break;
        }
    }

    private void setListener(View v) {
        plus_btn.setOnClickListener(this);
    }

    private class Recycler extends RecyclerView.Adapter<Recycler.ViewHolder> {

        private List<Item> mItemList;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView Name;
            public TextView symbol;
            public ViewHolder(View view) {
                super(view);
                Name = (TextView) view.findViewById(R.id.Subject);
                symbol=(TextView) view.findViewById(R.id.symbol);
            }
        }

        public Recycler(List<Item> ItemList) {
            mItemList = ItemList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_button, parent, false);
            ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {//????????????????????????????????????
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    String sub=mItemList.get(position).getName();

                    //????????????
                    SubFragments=new ArrayList<>();
                    Iterator<Item> iterator = total_SubjectList.iterator();
                    List<Item> lst1=new ArrayList<>();
                    List<Item> lst2=new ArrayList<>();
                    //???????????????????????????sharepreference
                    SharedPreferences sp=getActivity().getSharedPreferences("SubjectInfo", MODE_PRIVATE);
                    //???????????????
                    SharedPreferences.Editor editor=sp.edit();
                    //???total_Subject??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                    while (iterator.hasNext()){
                        Item next = iterator.next();
                        String name = next.getName();
                        if ((isIn(name,SubjectList)&&!name.equals(sub))||(isIn(name,toadd_SubjectList)&&name.equals(sub))){
                            lst1.add(new Item(name));
                            SubFragments.add(new SubjectFragment(name));
                            //???????????????
                            editor.putBoolean(name,true);
                        }
                        else{
                            lst2.add(new Item(name));
                            //???????????????
                            editor.putBoolean(name,false);
                        }
                    }
                    //????????????
                    editor.commit();
                    SubjectList= lst1;
                    toadd_SubjectList=lst2;
                    SubViewPager.setAdapter(new MyFragmentAdapter(getFragmentManager()));
                    radapter=new Recycler1(SubjectList);
                    recyclerView.setAdapter(radapter);
                    SubViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                        @Override
                        public void onPageSelected(int position) {
                            //position??????????????????????????????????????????????????????????????????????????????index??????????????????????????????????????????
                            radapter.mposition=position;
                            radapter.notifyDataSetChanged();
                        }
                    });
                    setListener(view);
                    initBottom();
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Item item = mItemList.get(position);
            holder.Name.setText(item.getName());
            if(isIn(item.getName(), SubjectList)){
                holder.symbol.setText("-");
            }
            else{
                holder.symbol.setText("+");
            }
        }

        @Override
        public int getItemCount() {
            return mItemList.size();
        }
    }

    public class Recycler1 extends RecyclerView.Adapter<Recycler1.ViewHolder> {

        private List<Item> mItemList;
        private int mposition=0;
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView Name;
            public ViewHolder(View view) {
                super(view);
                Name = (TextView) view.findViewById(R.id.Subject_Label);
            }
        }

        public Recycler1(List<Item> ItemList) {
            mItemList = ItemList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subjet_label, parent, false);
            ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {//????????????????????????????????????
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    String sub=mItemList.get(position).getName();
                    //????????????
                    mposition=holder.getAdapterPosition();
                    SubViewPager.setCurrentItem(position);
                    notifyDataSetChanged();
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Item item = mItemList.get(position);
            holder.Name.setText(item.getName());
            if (mposition != position) {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }else if (mposition ==  position) {
                holder.itemView.setBackgroundColor(Color.parseColor("#30B4FF"));
            }
        }

        @Override
        public int getItemCount() {
            return mItemList.size();
        }
    }

    private void initBottom(){
        adapter=new Recycler(SubjectList);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(),4);
        currentView.setLayoutManager(layoutManager);
        currentView.setAdapter(adapter);
        toadd_adapter =new Recycler(toadd_SubjectList);
        layoutManager = new GridLayoutManager(view.getContext(),4);
        toadd_View.setLayoutManager(layoutManager);
        toadd_View.setAdapter(toadd_adapter);
    }

    private void showBottomDialog() {
        //1?????????Dialog?????????style
        final Dialog dialog = new Dialog(this.getContext(), R.style.DialogTheme);
        //2???????????????
        View view = View.inflate(this.getContext(), R.layout.add_subject, null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //??????????????????
        window.setGravity(Gravity.BOTTOM);
        //??????????????????
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //?????????????????????
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        toadd_View = (RecyclerView) view.findViewById(R.id.toadd);
        currentView = (RecyclerView) view.findViewById(R.id.current);
        initBottom();
        dialog.show();
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

}
