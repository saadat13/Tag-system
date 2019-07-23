package com.example.tagsystemapplication;

import android.app.Dialog;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.example.tagsystemapplication.Objects.SystemObject;

public class Constants {

    public enum ROLE{EXPERT, FULL_EXPERT, MANAGER, UNDEFINDED}
    public enum TYPE{IMAGE, VIDEO, TEXT}


    public static void showOptions(View view, SystemObject object) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.my_item_options, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getTitle().toString()){
                    case "Add tag":
                        Dialog dialog = new Dialog(view.getContext());
                        dialog.setContentView(R.layout.layout_create_tag);
                        dialog.setTitle("Add tag");
                        EditText ed = dialog.findViewById(R.id.ed_tag);
                        Button add = dialog.findViewById(R.id.add);
                        Button cancel = dialog.findViewById(R.id.cancel);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String strTag = ed.getText().toString().trim();
                                if(strTag.isEmpty()){
                                    ed.setError("please enter something");
                                }else if(strTag.length() > 25){
                                    ed.setError("maximum length error");
                                }else{
                                    object.addTag(new MyTag(strTag));
                                    dialog.dismiss();
                                }
                            }
                        });
                        dialog.show();
                        break;
                    case "Remove tag":
                        break;
                    case "submit":
                        break;
                }
                return false;
            }
        });
        popup.show();
    }



}
