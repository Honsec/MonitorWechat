package genius.monitorwechat;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;



public class Utils_Alert {

	public static AlertDialog.Builder getDialogBuilder(Context context, int title, int message, boolean cancleable, int positive, OnClickListener positive_ls, int negtive, OnClickListener negtive_ls) {
		if (context == null)
			return null;
		AlertDialog.Builder alertDialogbuilder=null;
		alertDialogbuilder = new AlertDialog.Builder(context);
		if(title!=0)
			alertDialogbuilder.setTitle(title);
		if(message!=0)
		alertDialogbuilder.setMessage(message);
		alertDialogbuilder.setCancelable(cancleable);
		if(positive!=0)
			alertDialogbuilder.setPositiveButton(positive,positive_ls);
		if(negtive!=0)
			alertDialogbuilder.setNegativeButton(negtive, negtive_ls);
		return alertDialogbuilder;
	}


	public static void showAlertDialog_ad(Context context,int title, int message,boolean cancleable,int positive,OnClickListener positive_ls,int negtive,OnClickListener negtive_ls,OnKeyListener onKeyListener){

		if (context == null)
			return;
		try {
			AlertDialog.Builder alertDialogbuilder=null;
			alertDialogbuilder = new AlertDialog.Builder(context);
			if(title!=0)
				alertDialogbuilder.setTitle(title);
			if(message!=0)
				alertDialogbuilder.setMessage(message);
			alertDialogbuilder.setCancelable(cancleable);
			if(positive!=0)
				alertDialogbuilder.setPositiveButton(positive,positive_ls);
			if(negtive!=0)
				alertDialogbuilder.setNegativeButton(negtive, negtive_ls);
			if(onKeyListener!=null)
				alertDialogbuilder.setOnKeyListener(onKeyListener);
			alertDialogbuilder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	public static void showAlertDialog(Context context,int title, int message,boolean cancleable,int positive,OnClickListener positive_ls,int negtive,OnClickListener negtive_ls,OnKeyListener onKeyListener) {
			if (context == null)
				return;
		try {
			AlertDialog.Builder alertDialogbuilder=null;
			alertDialogbuilder = new AlertDialog.Builder(context);
			if(title!=0)
				alertDialogbuilder.setTitle(title);
			if(message!=0)
				alertDialogbuilder.setMessage(message);
			alertDialogbuilder.setCancelable(cancleable);
			if(positive!=0)
			alertDialogbuilder.setPositiveButton(positive,positive_ls);
			if(negtive!=0)
			alertDialogbuilder.setNegativeButton(negtive, negtive_ls);
			if(onKeyListener!=null)
			alertDialogbuilder.setOnKeyListener(onKeyListener);
			alertDialogbuilder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void showAlertDialog(Context context,int title, String message,boolean cancleable,int positive,OnClickListener positive_ls,int negtive,OnClickListener negtive_ls,OnKeyListener onKeyListener) {
		if (context == null)
			return;
		try {
			AlertDialog.Builder alertDialogbuilder=null;
			alertDialogbuilder = new AlertDialog.Builder(context);
			if(title!=0)
                alertDialogbuilder.setTitle(title);
			if(!TextUtils.isEmpty(message))
                alertDialogbuilder.setMessage(message);
			alertDialogbuilder.setCancelable(cancleable);
			if(positive!=0)
                alertDialogbuilder.setPositiveButton(positive,positive_ls);
			if(negtive!=0)
                alertDialogbuilder.setNegativeButton(negtive, negtive_ls);
			if(onKeyListener!=null)
            alertDialogbuilder.setOnKeyListener(onKeyListener);
			alertDialogbuilder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	
}
