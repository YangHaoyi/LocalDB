package com.yhy.loginsave;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * 作者 : YangHaoyi on 2016/7/18.
 * 邮箱 ：yanghaoyi@neusoft.com
 */
public class MenuDialogFragment extends DialogFragment implements View.OnClickListener {

    private View view;
    private FrameLayout backGround;
    private Button add,delete,edit,select,list;
    private MainActivity activity;

    public static MenuDialogFragment newInstance(){
        MenuDialogFragment menuDialogFragment = new MenuDialogFragment();
        return menuDialogFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialogfragment_menu,null);
        init();
        return view;
    }
    private void init(){
        add = (Button) view.findViewById(R.id.add);
        delete = (Button) view.findViewById(R.id.delete);
        edit = (Button) view.findViewById(R.id.edit);
        select = (Button) view.findViewById(R.id.select);
        list = (Button) view.findViewById(R.id.list);
        backGround = (FrameLayout) view.findViewById(R.id.backGround);

        backGround.setOnClickListener(this);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);
        select.setOnClickListener(this);
        list.setOnClickListener(this);

        activity = (MainActivity) getActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                activity.addRec();
                dismiss();
                break;
            case R.id.delete:
                activity.deleteRec();
                dismiss();
                break;
            case R.id.edit:
                activity.editRec();
                dismiss();
                break;
            case R.id.select:
                activity.queryRec();
                dismiss();
                break;
            case R.id.list:
                activity.showList();
                dismiss();
                break;
            case R.id.backGround:
                dismiss();
                break;
        }
    }
}
