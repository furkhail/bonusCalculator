package dnd.furkhail.bonuscalculator.presentation.view.adapter;

import android.util.Log;

import java.util.List;

import dnd.furkhail.bonuscalculator.domain.business.Ability;

public class AbilityAdapter extends ScoreAdapter<Ability>{

    private static final String TAG = "AbilityAdapterH";

    public AbilityAdapter(List<Ability> scores) {
        super(scores);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ability ability = mScores.get(position);
        holder.name.setText(ability.getName());
        holder.amount.setText(ability.getAmount()+"");
        holder.amount.setOnClickListener(v -> {
            Log.i(TAG, "onBindViewHolder: onclicklistener fired");
            onClickSubject.onNext(ability);
        });
    }
}
