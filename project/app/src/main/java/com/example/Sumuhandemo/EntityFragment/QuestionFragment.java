package com.example.Sumuhandemo.EntityFragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.Sumuhandemo.ExamActivity;
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
public class QuestionFragment extends Fragment {
    private List<Map> questionList;

    private RecyclerView questionView;
    private QuestionFragment.QuestionAdapter adapter;
    public View view;

    public QuestionFragment(){questionList = new ArrayList<>();}
    public QuestionFragment(List<Map> qList) {questionList = qList;}

    public class QuestionAdapter extends RecyclerView.Adapter<QuestionFragment.QuestionAdapter.ViewHolder> {

        private List<Map> qList;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView questionBody;
            public TextView questionAnswer;
            public ViewHolder(View view) {
                super(view);
                questionBody = (TextView) view.findViewById(R.id.question_body);
                questionAnswer = (TextView) view.findViewById(R.id.question_answer);
            }
        }

        public QuestionAdapter(List<Map> q) {
            qList = q;
        }

        @Override
        public QuestionFragment.QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entity_question_item, parent, false);
            QuestionFragment.QuestionAdapter.ViewHolder holder = new QuestionFragment.QuestionAdapter.ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(QuestionFragment.QuestionAdapter.ViewHolder holder, int position) {
            String body = (String)qList.get(position).get("qBody");
            holder.questionBody.setText(body);
            String answer = (String)qList.get(position).get("qAnswer");
            holder.questionAnswer.setText(answer);
            Long id = new Long((Integer)qList.get(position).get("id"));
            holder.questionBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ExamActivity.class);
                    intent.putExtra("qBody", body);
                    intent.putExtra("qAnswer", answer);
                    intent.putExtra("qId", id);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return qList.size();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.entity_question, container, false);
        adapter = new QuestionFragment.QuestionAdapter(questionList);
        questionView = (RecyclerView) view.findViewById(R.id.question_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        questionView.setLayoutManager(layoutManager);
        questionView.setAdapter(adapter);
        return view;
    }


}
