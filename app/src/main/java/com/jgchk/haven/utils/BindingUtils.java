package com.jgchk.haven.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.ui.main.results.ResultsAdapter;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.List;
import java.util.Map;

public final class BindingUtils {

    private BindingUtils() {
        // This utility class is not publicly instantiable
    }

    @BindingAdapter({"adapter"})
    public static void addResultsItems(RecyclerView recyclerView, List<Shelter> resultsItems) {
        AppLogger.d("BINDINGUTILS");
        ResultsAdapter adapter = (ResultsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(resultsItems);
        }
    }

    @BindingAdapter("valueChangedListener")
    public static void bindValueChangedListener(NumberPicker numberPicker, NumberPicker.OnValueChangeListener valueChangeListener) {
        numberPicker.setOnValueChangedListener(valueChangeListener);
    }
}
