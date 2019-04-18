package fei.li.criminalintent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mCrimeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = v.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateUI();

        return v;
    }

    private void updateUI() {
        List<Crime> crimes = CrimeLab.getLab(getContext()).getCrimes();
        mCrimeAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mCrimeAdapter);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent, int layoutId) {


            super(inflater.inflate(layoutId, parent, false));

            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);
        }

        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }


        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), mCrime.getTitle() + " is clicked", Toast.LENGTH_SHORT).show();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            if (viewType == 0) {

                return new CrimeHolder(LayoutInflater.from(getContext()), viewGroup, R.layout.list_item_crime);
            } else {
                return new CrimeHolder(LayoutInflater.from(viewGroup.getContext()), viewGroup, R.layout.list_item_crime_police);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder crimeHolder, int intType) {

        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position, @NonNull List<Object> payloads) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemViewType(int position) {
            if (mCrimes.get(position).ismNeedPolice()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
