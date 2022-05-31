package com.aqua.anroid.policynoticeapp.Calendar;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aqua.anroid.policynoticeapp.R;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/* 이벤트 유형의 배열 어댑터 확장 */
public class EventAdapter extends ArrayAdapter<Event>
{

    public EventAdapter(@NonNull Context context, List<Event> events)
    {
        super(context, 0, events); //super 호출 위해 resource 0 지정
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Event event = getItem(position);

        //이벤트 항목 가져옴
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

        TextView eventTitleTV = convertView.findViewById(R.id.eventTitleTV);
        TextView eventStartDateTV = convertView.findViewById(R.id.eventStartDateTV);
        TextView eventEndDateTV = convertView.findViewById(R.id.eventEndDateTV);

        String eventTitle = event.getTitle();
       /* String eventStartDateTV = event.getStartdate();
        String eventEndDateTV = event.getEnddate();*/


        eventTitleTV.setText(eventTitle);
    /*    eventStartDateTV.setText(eventStartDateTV);
        eventEndDateTV.setText(eventStartDateTV);*/

        return convertView;
    }

}
