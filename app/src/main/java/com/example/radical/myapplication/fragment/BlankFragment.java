package com.example.radical.myapplication.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radical.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "ARG_PAGE";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mPage;
    private ProgressDialog pd;
    /*private String mParam1;
    private String mParam2;*/


    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(int param1) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private boolean mHasLoadedOnce = false;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (this.isVisible()) {
            // we check that the fragment is becoming visible
            if (isVisibleToUser && !mHasLoadedOnce ) {

                // async http request here

                lazyLoad();
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        TextView textView=(TextView) view;
        textView.setText("Fragment Tab "+(mPage+1));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        setUserVisibleHint(true);
        super.onActivityCreated(savedInstanceState);
        //第一个fragment会调用
        if (getUserVisibleHint())
            lazyLoad();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            pd.dismiss();
        }
    };
    private void lazyLoad() {
        //如果没有加载过就加载，否则就不再加载了
        if(!mHasLoadedOnce){
            //加载数据操作
            mHasLoadedOnce=true;
        }
        pd = ProgressDialog.show(getActivity(), "", "加载中，请稍后……");
        Timer timer = new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        };
        timer.schedule(task,1000);
//        Toast.makeText(getActivity(),"Lazy Load",Toast.LENGTH_SHORT).show();
    }
}
