package com.aqua.anroid.policynoticeapp.Calendar;


import static com.aqua.anroid.policynoticeapp.Calendar.CalendarUtils.daysInMonthArray;
import static com.aqua.anroid.policynoticeapp.Calendar.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqua.anroid.policynoticeapp.R;

import java.time.LocalDate;
import java.util.ArrayList;



public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initWidgets(); //id 통해 목록 찾음
        //loadFromDBToMemory();
        CalendarUtils.selectedDate = LocalDate.now(); //현재 날짜
        setMonthView(); //화면 설정
        setOnClickListener();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    /* db
    private void loadFromDBToMemory()
    {
        SQLiteManager sqLiteManager = SQLiteManager.instance
    }*/

    private void setMonthView()
    {
        //년 월 텍스트뷰
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));

        //해당 월 날짜 가져오기
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);

        //레이아웃 설정, 열 7개
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

        setEventAdapter();
    }

    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        //현재 날짜로 변경하는 함수를 호출하여
        //CalendarUtils.selectedDate 가 날짜와 같도록 설정
        if(date != null)
        {
            String message = String.valueOf(date);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            CalendarUtils.selectedDate = date;
            setMonthView();

        }
    }

    // 이벤트 Adapter 제공
    private void setEventAdapter()
    {
        //ID 로 목록 찾고 리스트 호출
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    // event item 클릭 시
    private void setOnClickListener() {
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event selectedEvent = (Event) eventListView.getItemAtPosition(position);

                Intent eventEditIntent = new Intent(getApplicationContext(), EventEditActivity.class);
                eventEditIntent.putExtra(Event.Event_EDIT_EXTRA, selectedEvent.getId());
                startActivity(eventEditIntent);

            }
        });
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(CalendarActivity.this,EventEditActivity.class));
    }

    // 재개될 때마다 다시 로드되도록 EventAdapter 호출
    //Activity가 사용자와 상호작용하기 바로 전에 호출됨
    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdapter();
    }

}