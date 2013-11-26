package com.example.grouped;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.grouped.models.Group;
import com.example.grouped.models.Member;
import com.example.grouped.models.Message;

@SuppressLint({ "ValidFragment"}) public class MessageBoardFragment extends Fragment {
	
		private Member me;
		private Group group;
	
		public MessageBoardFragment(Member m){
			//me = m;
			group = new Group();
			me = new Member();
			Member other = new Member();
			me.setMessages(buildArrayListOfMessage());
			me.setId(1234);
			me.setMe(true);
			other.setId(1235);
			other.setNickname("Other");
			other.setMe(false);
			group.addMember(me);
			group.addMember(other);
			
		}
	
		public ArrayList<Message> buildArrayListOfMessage(){
			ArrayList<Message> messages = new ArrayList<Message>();
			
			for(int i = 1; i <= 10; i++) {
				Message message = new Message();
				message.setId(i);
				message.setMessage("MessageMessageMessageMessageMessageMessageMessageMessageMessageMessage" +
						"MessageMessageMessageMessageMessageMessageMessageMessageMessageMessageMessageMessage"+i);
				message.setTimeStamp("TimeStamp"+i);
				if (i%2 == 0) {
					message.setMemberId(1234);
				} else {
					message.setMemberId(1235);
				}
				messages.add(message);
			}
			
			return messages;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_message_board, container,
					false);
			ScrollView scroll = (ScrollView)rootView.findViewById(R.id.member_container);
			RelativeLayout r = (RelativeLayout)scroll.findViewById(R.id.container);
			
			int tempId = 0;
			for(Message message : me.getMessages()) {
//				TextView messageBuilder = new TextView(getActivity());
//				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//						400,
//						RelativeLayout.LayoutParams.WRAP_CONTENT);
//				messageBuilder.setText(message.getMessage());
//				messageBuilder.setId(tempId);
//				if (tempId> 0) {
//					params.addRule(RelativeLayout.BELOW, tempId-1);
//				}
//				if (group.getMemberById(message.getMemberId()).isMe()) {
//					messageBuilder.setTextColor(Color.parseColor("#674ea7"));
//					messageBuilder.setBackgroundResource(R.drawable.outgoing_message);
//					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//				} else {
//					messageBuilder.setTextColor(Color.parseColor("#ffffff"));
//					messageBuilder.setBackgroundResource(R.drawable.incoming_message);
//					params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//				}
//				
				RelativeLayout messageBuilder = buildMessage(message);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
				if (tempId > 0){
					params.addRule(RelativeLayout.BELOW, tempId);
				}
				if(group.getMemberById(message.getMemberId()).isMe()){
					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				}
				tempId = messageBuilder.getId();
				r.addView(messageBuilder, params);
			}
			return rootView;
		}
		
	private TextView buildTop(Message message) {
		TextView top = new TextView(getActivity());
		top.setId(1000*(int)message.getId()+1);
		top.setPadding(20, 20, 20, 20);
		
		//Determine Sender Nickname or Me
		if (group.getMemberById(message.getMemberId()).isMe()){
			top.setText("Me at " +
					message.getTimeStamp());
			top.setBackgroundResource(R.drawable.outgoing_message_bubble_top_noline);
			top.setTextColor(Color.parseColor("#674ea7"));
		} else {
			top.setText(group.getMemberById(message.getMemberId()).getNickname() +
					" at " +
					message.getTimeStamp());
			top.setBackgroundResource(R.drawable.incoming_message_bubble_top_noline);
			top.setTextColor(Color.parseColor("#ffffff"));
		}
		top.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		return top;
	}
	
	private TextView buildMiddle(Message message) {
		TextView middle = new TextView(getActivity());
		middle.setId(1000*(int)message.getId()+2);
		middle.setPadding(20, 0, 20, 0);
		middle.setText(message.getMessage());
		if(group.getMemberById(message.getMemberId()).isMe()){
			middle.setBackgroundResource(R.drawable.outgoing_message_bubble_middle_noline);
			middle.setTextColor(Color.parseColor("#674ea7"));
		} else {
			middle.setBackgroundResource(R.drawable.incoming_message_bubble_middle_noline);
			middle.setTextColor(Color.parseColor("#ffffff"));
		}
		
		return middle;
	}
	
	private ImageView buildBottom(Message message) {
		ImageView bottom = new ImageView(getActivity());
		bottom.setScaleType(ScaleType.FIT_XY);
		if (group.getMemberById(message.getMemberId()).isMe()){
			bottom.setImageResource(R.drawable.outgoing_message_bubble_bottom_noline);
		} else {
			bottom.setImageResource(R.drawable.incoming_message_bubble_bottom_noline);
		}
		return bottom;
	}
	
	private RelativeLayout buildMessage(Message message) {
		RelativeLayout messageBuilder = new RelativeLayout(getActivity());
		RelativeLayout.LayoutParams middleParams = new RelativeLayout.LayoutParams(
				400, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams bottomParams = new RelativeLayout.LayoutParams(
				400, RelativeLayout.LayoutParams.WRAP_CONTENT);
		messageBuilder.setId(1000*(int)message.getId());
		messageBuilder.addView(buildTop(message), 400, RelativeLayout.LayoutParams.WRAP_CONTENT);
		middleParams.addRule(RelativeLayout.BELOW, 1000*(int)message.getId()+1);
		messageBuilder.addView(buildMiddle(message), middleParams);
		bottomParams.addRule(RelativeLayout.BELOW, 1000*(int)message.getId()+2);
		messageBuilder.addView(buildBottom(message), bottomParams);
		return messageBuilder;
	}
} 
	


