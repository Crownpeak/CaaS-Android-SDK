package com.evidon.privacy_caas.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.view.LayoutInflater;


//import com.afollestad.materialdialogs.DialogAction;
//import com.afollestad.materialdialogs.GravityEnum;
//import com.afollestad.materialdialogs.MaterialDialog;
import com.evidon.privacy_caas.R;

import org.json.JSONException;
import org.json.JSONObject;


public class DoNotSellDialog extends DialogFragment {
    public static final String FRAGMENT_NAME = "DoNotSell";

    EditText name, email, address, comments;
    private static final String BASEPATH = "https://i.evidondev.com";
//TODO
//    private interface CaaSAPI {
//        @Headers({
//                "Token: f73a0d699ee149da803d02a45a381107",
//                "Referer: https://caas-demo.evidon.com"
//        })
//        @GET("/api/v1")
//        void getMyData(@QueryMap Map<String, String> params, Callback<String> callback);
//    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View view = factory.inflate(R.layout.dialog_do_not_sell, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Do not Sell my information");

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_do_not_sell, null))
                // Add action buttons
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                //This is the input I can't get text from
                                name =(EditText)view.findViewById(R.id.FullName);
                                String query = name.getText().toString();
                                Log.i("DoNotSell", query);

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                DoNotSellDialog.this.getDialog().cancel();
                            }
                        });
        return builder.create();
    }

//
//        MaterialDialog dialog = new MaterialDialog.Builder(getActivity()).
//                positiveText("Submit").
//                negativeText(getString(R.string.cancel)).
//                title(getString(R.string.do_not_sell)).
//                titleGravity(GravityEnum.CENTER).
//                customView(R.layout.dialog_do_not_sell, false)
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        name = (EditText) dialog.findViewById(R.id.FullName);
//                        email = (EditText) dialog.findViewById(R.id.email);
//                        address = (EditText) dialog.findViewById(R.id.postalAddress);
//                        comments = (EditText) dialog.findViewById(R.id.comments);
//
//                        sendDonotSellData();
//                    }
//                })
//                .onNegative(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        dialog.dismiss();
//                    }
//                }).build();
//
//
//        return dialog;
//    }

    private void sendDonotSellData() {
//TODO
//        final RestAdapter rest = new RestAdapter.Builder().setEndpoint(BASEPATH).build();
//        CaasApiInterface service = rest.create(CaasApiInterface.class);

        final JSONObject data = new JSONObject();
        try {
            data.put("name", name.getText().toString());
            data.put("email", email.getText().toString());
            data.put("address", address.getText().toString());
            data.put("comments", comments.getText().toString());
            data.put("uniqueId", "test23423432");
            data.put("applicationName", "Privacy_CaaS");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("JSONData", String.valueOf(data));
//TODO
//        String encodedData = HttpRequest.Base64.encode(String.valueOf(data));
//        Log.d("Encoded Value", encodedData);
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("d", encodedData);
//        params.put("r", "CCPA DoNotSell");
//
//        service.getMyData(params, new Callback<String>() {
//            @Override
//            public void success(String s, Response response) {
//                Log.d("Success", String.valueOf(data));
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                // ... do some stuff here.
//                Log.d("Failed to send Data", String.valueOf(error));
//            }
//        });
    }
}