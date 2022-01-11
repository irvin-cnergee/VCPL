/**
 * 
 */
package com.cnergee.calculator.obj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cnergee.myapp.instanet.R;

import java.util.ArrayList;




/**
 * @author Sandip
 *
 */
public class Calci_Speed_Adapter extends ArrayAdapter<Calci_Speed>{
	ArrayList<Calci_Speed> alNumbers;
	Context ctx;
	String data;
	int resource_id;
		public Calci_Speed_Adapter(Context context, int textViewResourceId,
				ArrayList<Calci_Speed> alNumbers,String data) {
			super(context, textViewResourceId, alNumbers);
			// TODO Auto-generated constructor stub
			this.alNumbers=alNumbers;
			ctx=context;
			this.data=data;
			this.resource_id=textViewResourceId;
		}
		
		public class ViewHolder{
			TextView tvNumber;
			TextView tvUnit;
			LinearLayout ll_gv;
		}

		@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) ctx
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ViewHolder holder= new ViewHolder();
			Calci_Speed spd=(Calci_Speed)this.getItem(position);
			if(convertView==null){
				if(data.equalsIgnoreCase("speed")){
				convertView=inflater.inflate(resource_id, null);
				}
				else{
				convertView=inflater.inflate(resource_id, null);
				}
				holder.tvNumber=(TextView) convertView.findViewById(R.id.tvNumber);
				holder.tvUnit=(TextView)convertView.findViewById(R.id.tvUnit);
				holder.ll_gv=(LinearLayout) convertView.findViewById(R.id.ll_gv);
				
				 convertView.setTag(holder);
			}
			else{
				 holder = (ViewHolder) convertView.getTag();
			}
			holder.tvNumber.setText(spd.getCalc_speed_value());
			
			/*if(PlanCalculatorActivity.selected_gv_speed_position.equals(String.valueOf(position))){
				
				holder.ll_gv.setBackgroundColor(R.color.kesari_color);
			}
			else{
				holder.ll_gv.setBackgroundResource(R.drawable.num_box_new);
			}*/
			
			if(data!=null){
				if(data.equalsIgnoreCase("speed")){
			if(position==0){
				holder.tvUnit.setVisibility(View.VISIBLE);
				holder.tvUnit.setText("kbps");
			}
			else{
				holder.tvUnit.setVisibility(View.GONE);
			}
				}
			}
				return convertView;
			}
		
		
}
