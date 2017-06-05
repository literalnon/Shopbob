package dmproject.moviebuff.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

import dmproject.moviebuff.Activity.TaskActivity;
import dmproject.moviebuff.R;

/**
 * Created by Dmitry on 02.08.2016.
 */
public class AdapterForAnswer extends BaseAdapter{

        String answer;
        LayoutInflater inflater;
        Context context;


        public AdapterForAnswer(Context context, String answer){
            this.answer = answer;
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return answer.length();
        }

        @Override
        public Object getItem(int position) {
            return answer.charAt(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (view == null){
                view = inflater.inflate(R.layout.btn_custom_fill_answer, parent, false);
                TaskActivity.answerButtons.add(view);
            }

            return view;
        }
    }
