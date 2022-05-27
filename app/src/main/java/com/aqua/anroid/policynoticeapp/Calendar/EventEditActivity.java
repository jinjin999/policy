package com.aqua.anroid.policynoticeapp.Calendar;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.aqua.anroid.policynoticeapp.MainActivity;
import com.aqua.anroid.policynoticeapp.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class EventEditActivity extends AppCompatActivity {
    private EditText eventTitleET;
    private TextView startDateTV, endDateTV;
    private Button deleteEventBtn, eventDatePickerBtn;

    private Event selectedEvent;
    private LocalTime time; // 현지 시간으로 시간 호출
    Event event = new Event();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now(); // 지금 현지 시간으로 초기화

       /* //날짜 Text 선택한 날짜로 설정
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
*/
        checkForEditEvent();

    }

    private void initWidgets()
    {
        eventTitleET = findViewById(R.id.eventTitleET);
        deleteEventBtn = findViewById(R.id.deleteEventBtn);
        eventDatePickerBtn = findViewById(R.id.eventDatePickerBtn);

        startDateTV = findViewById(R.id.startDateTV);
        endDateTV = findViewById(R.id.endDateTV);
    }

    private void checkForEditEvent()
    {
        Intent previousIntent = getIntent();

        int passedEventID = previousIntent.getIntExtra(Event.Event_EDIT_EXTRA, -1);
        selectedEvent = Event.getEventForID(passedEventID);

        // 이벤트 편집이 있음을 의미하는 선택된 이벤트 찾았을 때
        if (selectedEvent != null)
        {
            eventTitleET.setText(selectedEvent.getTitle());
            startDateTV.setText(selectedEvent.getStartdate());
            endDateTV.setText(selectedEvent.getEnddate());

          /*  Log.e("etitle:", selectedEvent.getTitle());
            Log.e("estartdate:", selectedEvent.getStartdate());
            Log.e("eenddate:", selectedEvent.getEnddate());*/
        }
        else
        {
            //새로운 이벤트 생성 시 삭제 버튼 숨김
            deleteEventBtn.setVisibility(View.INVISIBLE);
        }
    }

    public void saveEventAction(View View)
    {
        String eventTitle = eventTitleET.getText().toString();
        String eventStartDate = startDateTV.getText().toString();
        String eventEndDate = endDateTV.getText().toString();


        if (selectedEvent == null) {
            int id = Event.eventsList.size();
            Event newEvent = new Event(id, eventTitle, CalendarUtils.selectedDate, eventStartDate, eventEndDate, time);
            //Event newEvent = new Event (eventTitle,CalendarUtils.selectedDate, time);

            Event.eventsList.add(newEvent); // 새 이벤트를 이벤트 목록에 추가
        }
        // 편집 모드
        else
        {
            //선택한 메모에 제목을 가져와 동일하게 지정
            selectedEvent.setTitle(eventTitle);
            selectedEvent.setStartdate(eventStartDate);
            selectedEvent.setEnddate(eventEndDate);

            // db 업데이트 하기
            // ...
        }
        startActivity(new Intent(this, CalendarActivity.class));

    }

    // 이벤트 삭제
    public void deleteEventAction(View view) {
        // 새 날짜를 호출하여 삭제된 시간을 제공
        selectedEvent.setDeleted(new Date());
        //db 설정
        //db 업데이트

        startActivity(new Intent(this, CalendarActivity.class));
    }

    //날짜 선택
    public void datepickerAction(View view) {
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();

        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("날짜를 선택해주세요");
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        materialDateBuilder.setSelection(today);

        //미리 날짜 선택
//        builder.setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()));
        final MaterialDatePicker materialDatePicker = builder.build();
        materialDatePicker.show(getSupportFragmentManager(), "Date_PICKER");

        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date startdate = new Date();
                        Date enddate = new Date();

                        startdate.setTime(selection.first);
                        enddate.setTime(selection.second);

                        String startdateString = simpleDateFormat.format(startdate);
                        String enddateString = simpleDateFormat.format(enddate);

                        startDateTV.setText(startdateString);
                        endDateTV.setText(enddateString);

                        //선택한 날짜 객체 저장
                        event.setStartdate(startdateString);
                        event.setEnddate(enddateString);
                    }
                });

    }
}
