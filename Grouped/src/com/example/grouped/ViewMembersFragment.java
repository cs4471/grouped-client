package com.example.grouped;

import com.example.grouped.models.Group;
import com.example.grouped.models.Member;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

@SuppressLint({ "ValidFragment"}) public class ViewMembersFragment extends Fragment {
	private Group group;

	@SuppressLint("ValidFragment") public ViewMembersFragment(Group group) {
		//this.group = group;
		this.group = buildMockedGroup();
	}

	private Group buildMockedGroup() {
		Group group = new Group();
		
		for(int i = 0; i <= 10; i++){
			Member member = new Member();
			if (i == 0){
				member.setMe(true);
			}
			member.setNickname("Group Member" + i);
			member.setStatus("Status"+i);
			member.setLastCheckin(i%2);
			member.setMe(false);
			member.setId(i);
			group.addMember(member);
		}
		
		return group;
	}
	
	private RelativeLayout buildRelativeLayout(){
		RelativeLayout members = new RelativeLayout(getActivity());
		int i = 0;
		
		for (Member member : this.group.getMembers()) {
			RelativeLayout memberBox = new RelativeLayout(getActivity());
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			memberBox.setBackgroundResource(R.drawable.green_border);
			addNickname(memberBox, member.getNickname());
			addStatus(memberBox, member.getStatus());
			addCheckIn(memberBox);
			memberBox.setId(i);
			params.setMargins(5, 5, 5, 5);
			if(i > 0){
				params.addRule(RelativeLayout.BELOW, i-1);
			}
			i++;
			members.addView(memberBox, params);
		}
		return members;
	}
	
	private TextView buildText(Context context){
		TextView temp = new TextView(context);
		temp.setTextSize((float) 15);
		return temp;		
	}
	
	private TextView buildNickname(String nickname) {
		TextView temp = new TextView(getActivity());
				temp.setText(nickname);
		temp.setTextSize((float)25);
		temp.setBackgroundResource(R.color.steel_grey);
		temp.setTextColor(Color.parseColor("#674ea7"));
		temp.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		return temp;
	}
	
	private TextView buildStatusTitle(){
		TextView text = buildText(getActivity());
		text.setText("Status: ");
		text.setTextColor(Color.parseColor("#674ea7"));
		text.setId(1000);
		return text;
	}
	
	private TextView buildStatus(String status){
		TextView text = buildText(getActivity());
		text.setText(status);
		text.setTextColor(Color.parseColor("#ffffff"));
		text.setId(1100);
		return text;
	}
	
	private TextView buildCheckInTitle(){
		TextView text = buildText(getActivity());
		text.setText("Checked In Recently: ");
		text.setTextColor(Color.parseColor("#674ea7"));
		text.setId(2000);
		text.setPadding(0, 0, 0, 10);
		return text;
	}
	
	private TextView buildCheckInContent() {
		TextView text = buildText(getActivity());
		text.setText("Yes");
		text.setTextColor(Color.parseColor("#ffffff"));
		text.setId(2100);
		return text;
	}
	
	private void addNickname(RelativeLayout memberBox, String nickname) {
		LinearLayout nicknameBackground = new LinearLayout(getActivity());
		LinearLayout.LayoutParams nicknameParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT);
		nicknameParams.setMargins(0, 0, 0, 6);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		TextView text = buildNickname(nickname);	
		params.setMargins(20, 10, 20, 0);
		nicknameBackground.setId(999);
		nicknameBackground.setBackgroundResource(R.color.plum_purple);
		nicknameBackground.addView(text, nicknameParams);
		memberBox.addView(nicknameBackground, params);
	}

	private void addStatus(RelativeLayout memberBox, String status){
		TextView statusTitle = buildStatusTitle();
		TextView statusText = buildStatus(status);
		RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		titleParams.setMargins(0, 10, 0, 0);
		titleParams.addRule(RelativeLayout.ALIGN_LEFT, 999);
		titleParams.addRule(RelativeLayout.BELOW, 999);
		textParams.addRule(RelativeLayout.ALIGN_TOP, 1000);
		textParams.addRule(RelativeLayout.RIGHT_OF, 1000);
		memberBox.addView(statusTitle, titleParams);
		memberBox.addView(statusText, textParams);	
	}
	
	private void addCheckIn(RelativeLayout memberBox) {
		TextView checkInTitle = buildCheckInTitle();
		TextView checkInText = buildCheckInContent();
		RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		titleParams.setMargins(0, 10, 0, 0);
		titleParams.addRule(RelativeLayout.ALIGN_LEFT, 1000);
		titleParams.addRule(RelativeLayout.BELOW, 1000);
		textParams.addRule(RelativeLayout.ALIGN_TOP, 2000);
		textParams.addRule(RelativeLayout.RIGHT_OF, 2000);
		memberBox.addView(checkInTitle, titleParams);
		memberBox.addView(checkInText, textParams);	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_view_members, container,
				false);
		RelativeLayout members = buildRelativeLayout();
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		ScrollView membersContainer = (ScrollView) rootView.findViewById(R.id.member_container);
		membersContainer.addView(members, params);
		return rootView;
	}


	
}
