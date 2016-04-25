package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;

/**
 * Created by 187835 on 4/11/2016.
 */
public class MoonFragment extends Fragment {

    private String myName = "Moon";
    private int myPosition;

    public MoonFragment(int position) {
        this.myPosition = position;
    }
    public MoonFragment(int position, String myName) {
        this.myPosition = position;
        this.myName = myName;
    }

    private class LineHolder extends RecyclerView.ViewHolder {
        private Button b;
        public LineHolder(View itemView) {
            super(itemView);
            b =(Button) itemView.findViewById(R.id.button);
        }
    }

    private class LineAdapter extends RecyclerView.Adapter<LineHolder> {
        @Override
        public LineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //Button b = new Button(getActivity());
            View v = layoutInflater.inflate(R.layout.list_layout, parent, false);
            return new LineHolder(v);
        }

        @Override
        public void onBindViewHolder(LineHolder holder, int position) {
            Button b = (Button) holder.itemView.findViewById(R.id.button);
            b.setText("Strona: " + Integer.toString(myPosition) + "   Liczba: " + Integer.toString(position));
        }

        @Override
        public int getItemCount() {
            return 15;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout, container, false);
        RecyclerView rv = (RecyclerView) v.findViewById(R.id.listView);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(new LineAdapter());
        return v;
    }

    public String getMyName() {
        return myName;
    }

    public int getMyPosition() {
        return myPosition;
    }
}
