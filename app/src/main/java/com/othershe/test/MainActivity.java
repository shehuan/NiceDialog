package com.othershe.test;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.NiceDialog1;
import com.othershe.nicedialog.ViewHolder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void showDialog(View view) {
//        new MyDialog().show(getSupportFragmentManager(), "");

        new NiceDialog1()
                .setLayoutId(R.layout.test)
                .setOutCancel(false)
                .setMargin(50)
                .setDimAmount(0.2f)
                .setShowBottom(false)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final DialogFragment dialog) {
                        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                }).show(getSupportFragmentManager());
    }
//    public static class MyDialog extends NiceDialog {
//
//        @Override
//        public int initLayoutId() {
//            return R.layout.test;
//        }
//
//        @Override
//        public void convertView(ViewHolder holder) {
//            holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dismiss();
//                }
//            });
//        }
//
//    }
}
