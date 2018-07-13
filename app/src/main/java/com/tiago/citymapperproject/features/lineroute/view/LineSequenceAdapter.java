package com.tiago.citymapperproject.features.lineroute.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tiago.citymapperproject.R;
import com.tiago.citymapperproject.features.lineroute.model.LineStation;
import java.util.ArrayList;
import java.util.List;

public class LineSequenceAdapter
    extends RecyclerView.Adapter<LineSequenceAdapter.LineStationViewHolder> {

  public static int VIEW_TYPE_LINE_STATIONS = 0;

  private Context context;
  private LayoutInflater inflater;
  private List<LineStation> items;

  public LineSequenceAdapter(Context context) {
    this.context = context;
    this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.items = new ArrayList<>();
  }

  @NonNull @Override
  public LineStationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new LineStationViewHolder(inflater.inflate(R.layout.item_line_station, parent, false));
  }

  @Override public int getItemViewType(int position) {
    return VIEW_TYPE_LINE_STATIONS;
  }

  @Override public void onBindViewHolder(@NonNull LineStationViewHolder holder, int position) {
    holder.bind(items.get(position));
  }

  @Override public int getItemCount() {
    return items.size();
  }

  public void setItems(List<LineStation> items) {
    this.items.clear();
    this.items.addAll(items);
    notifyDataSetChanged();
  }

  static class LineStationViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtName) TextView txtName;
    @BindView(R.id.viewUserPosition) View viewUserPosition;
    private GradientDrawable drawable;

    public LineStationViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

      drawable = new GradientDrawable();
      drawable.setShape(GradientDrawable.OVAL);
      drawable.setColor(Color.BLUE);
      viewUserPosition.setBackground(drawable);
    }

    private void bind(LineStation lineStation) {
      txtName.setText(lineStation.getName());

      if (lineStation.isSelected()) {
        TextViewCompat.setTextAppearance(txtName, R.style.BigBoldText);
      } else {
        TextViewCompat.setTextAppearance(txtName, R.style.MediumText_Black);
        txtName.setTypeface(null, Typeface.NORMAL); // doesn't remove bold otherwise :(
      }

      viewUserPosition.setVisibility(lineStation.isClosest() ? View.VISIBLE : View.GONE);
    }
  }
}
