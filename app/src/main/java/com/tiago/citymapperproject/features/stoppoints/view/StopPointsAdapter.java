package com.tiago.citymapperproject.features.stoppoints.view;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import com.tiago.citymapperproject.R;
import com.tiago.citymapperproject.core.utils.ListUtils;
import com.tiago.citymapperproject.features.arrivaltimes.model.ArrivalTime;
import com.tiago.citymapperproject.features.arrivaltimes.viewmodel.ArrivalTimesViewModel;
import com.tiago.citymapperproject.features.stoppoints.model.StopPoint;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.subjects.PublishSubject;
import java.util.ArrayList;
import java.util.List;

public class StopPointsAdapter extends RecyclerView.Adapter<StopPointsAdapter.StopPointViewHolder> {

  public static int VIEW_TYPE_STOP_POINTS = 0;

  private Context context;
  private LayoutInflater inflater;
  private List<StopPoint> items;
  private LifecycleOwner lifecycleOwner;
  private ArrivalTimesViewModel arrivalTimesViewModel;

  private PublishSubject<ArrivalTime> onClickArrival = PublishSubject.create();

  public StopPointsAdapter(Context context, LifecycleOwner lifecycleOwner,
      ArrivalTimesViewModel arrivalTimesViewModel) {
    this.context = context;
    this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.items = new ArrayList<>();
    this.lifecycleOwner = lifecycleOwner;
    this.arrivalTimesViewModel = arrivalTimesViewModel;
  }

  @NonNull @Override
  public StopPointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new StopPointViewHolder(inflater.inflate(R.layout.item_stop_point, parent, false),
        onClickArrival, lifecycleOwner, arrivalTimesViewModel);
  }

  @Override public int getItemViewType(int position) {
    return VIEW_TYPE_STOP_POINTS;
  }

  @Override public void onBindViewHolder(@NonNull StopPointViewHolder holder, int position) {
    holder.bind(context, items.get(position));
  }

  @Override public int getItemCount() {
    return items.size();
  }

  @Override public void onViewDetachedFromWindow(@NonNull StopPointViewHolder holder) {
    holder.onViewDetachedFromWindow();
    super.onViewDetachedFromWindow(holder);
  }

  public void setItems(List<StopPoint> items) {
    this.items.clear();
    this.items.addAll(items);
    notifyDataSetChanged();
  }

  public StopPoint getItemAtPosition(int position) {
    if (items.size() > 0 && position < items.size()) {
      return items.get(position);
    } else {
      return null;
    }
  }

  static class StopPointViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtName) TextView txtName;
    @BindViews({ R.id.txtArrival1, R.id.txtArrival2, R.id.txtArrival3 }) List<TextView> txtArrivals;

    private PublishSubject<ArrivalTime> subject;
    private LifecycleOwner lifecycleOwner;
    private ArrivalTimesViewModel arrivalTimesViewModel;
    private StopPoint stopPoint;

    public StopPointViewHolder(View itemView, PublishSubject<ArrivalTime> subject,
        LifecycleOwner lifecycleOwner, ArrivalTimesViewModel arrivalTimesViewModel) {
      super(itemView);
      this.subject = subject;
      this.lifecycleOwner = lifecycleOwner;
      this.arrivalTimesViewModel = arrivalTimesViewModel;
      ButterKnife.bind(this, itemView);
    }

    public void onViewDetachedFromWindow() {
      arrivalTimesViewModel.removeArrivals(stopPoint.getNaptanId());
    }

    private void bind(Context context, StopPoint stopPoint) {
      this.stopPoint = stopPoint;
      txtName.setText(stopPoint.getCommonName());
      arrivalTimesViewModel.loadArrivals(stopPoint.getNaptanId())
          .observe(lifecycleOwner, arrivalTimes -> {
            for (int i = 0; i < txtArrivals.size(); i++) {
              TextView txtArrival = txtArrivals.get(i);
              setArrivalTime(context, ListUtils.getOrNull(arrivalTimes, i), txtArrival);
            }
          });
    }

    private void setArrivalTime(Context context, ArrivalTime arrivalTime, TextView txtArrival) {
      txtArrival.setVisibility(arrivalTime == null ? View.GONE : View.VISIBLE);
      txtArrival.setText(
          context.getString(R.string.stop_points_arrival_time, arrivalTime.getTimeToStationInMin(),
              arrivalTime.getLineName()));
      txtArrival.setOnClickListener(clickedView -> subject.onNext(arrivalTime));
    }
  }

  public Flowable<ArrivalTime> onArrivalClick() {
    return onClickArrival.toFlowable(BackpressureStrategy.LATEST);
  }
}
