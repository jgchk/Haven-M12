package com.jgchk.haven.ui.main.results;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.databinding.ItemResultsEmptyViewBinding;
import com.jgchk.haven.databinding.ItemResultsViewBinding;
import com.jgchk.haven.ui.base.BaseViewHolder;
import com.jgchk.haven.utils.AppLogger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableConverter;
import io.reactivex.subjects.PublishSubject;

public class ResultsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0, VIEW_TYPE_NORMAL = 1;

    private final List<ResultsItemViewModel> mResultsList;

    private final PublishSubject<Integer> onClickSubject = PublishSubject.create();

    public ResultsAdapter() {
        this.mResultsList = new ArrayList<>();
    }

    public Observable<Integer> getPositionClicks() {
        return onClickSubject;
    }

    @Override
    public int getItemCount() {
        if (mResultsList.isEmpty()) {
            return 1;
        } else {
            return mResultsList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mResultsList.isEmpty()) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ItemResultsViewBinding resultsViewBinding = ItemResultsViewBinding
                        .inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ResultsViewHolder(resultsViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemResultsEmptyViewBinding emptyViewBinding = ItemResultsEmptyViewBinding
                        .inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    public void addItems(List<Shelter> resultsList) {
        AppLogger.d("RESULTSADAPTER");
        for (Shelter shelter : resultsList) {
            mResultsList.add(new ResultsItemViewModel(shelter.name, shelter.distance, shelter.vacancies));
        }
        notifyDataSetChanged();
    }

    public void clearItems() {
        mResultsList.clear();
    }

    public class EmptyViewHolder extends BaseViewHolder {

        private final ItemResultsEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemResultsEmptyViewBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            ResultsEmptyItemViewModel emptyItemViewModel = new ResultsEmptyItemViewModel();
            mBinding.setViewModel(emptyItemViewModel);
        }
    }

    public class ResultsViewHolder extends BaseViewHolder {

        private final ItemResultsViewBinding mBinding;

        public ResultsViewHolder(ItemResultsViewBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final ResultsItemViewModel mResultsItemViewModel = mResultsList.get(position);
            mBinding.setViewModel(mResultsItemViewModel);
            mBinding.executePendingBindings();
            mBinding.getRoot().setOnClickListener(v -> onClickSubject.onNext(position));
        }
    }
}
