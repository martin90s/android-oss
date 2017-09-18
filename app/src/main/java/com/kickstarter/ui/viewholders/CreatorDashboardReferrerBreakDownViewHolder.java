package com.kickstarter.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.kickstarter.R;
import com.kickstarter.services.apiresponses.ProjectStatsEnvelope;
import com.kickstarter.ui.views.ReferrerBreakdownView;
import com.kickstarter.viewmodels.CreatorDashboardReferrerBreakdownHolderViewModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.kickstarter.libs.rx.transformers.Transformers.observeForUI;
import static com.kickstarter.libs.utils.ObjectUtils.requireNonNull;

public class CreatorDashboardReferrerBreakDownViewHolder extends KSViewHolder {
  private final CreatorDashboardReferrerBreakdownHolderViewModel.ViewModel viewModel;
  private final ReferrerBreakdownView referrerBreakdownView;
  protected @Bind(R.id.referrer_breakdown_chart_layout) LinearLayout referrerBreakdownLayout;


  public CreatorDashboardReferrerBreakDownViewHolder(final @NonNull View view) {
    super(view);
    ButterKnife.bind(this, view);
    this.referrerBreakdownView = new ReferrerBreakdownView(context());
    this.referrerBreakdownLayout.addView(referrerBreakdownView);
    this.referrerBreakdownView.invalidate(0,0,view.getWidth(), view.getHeight());
    this.referrerBreakdownView.invalidate();

    this.viewModel = new CreatorDashboardReferrerBreakdownHolderViewModel.ViewModel(environment());

    this.viewModel.outputs.customReferrerPercent()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(percent -> this.referrerBreakdownView.setCustomAngleAndColor(0d, percent * 360d));

    this.viewModel.outputs.externalReferrerPercent()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(percent -> this.referrerBreakdownView.setExternalAngleAndColor(0d, percent * 360d));

    this.viewModel.outputs.internalReferrerPercent()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(percent -> this.referrerBreakdownView.setInternalAngleAndColor(0d, percent * 360d));

    this.viewModel.outputs.unknownReferrerPercent()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(percent -> this.referrerBreakdownView.setUnknownAngleAndColor(0d, percent * 360d));
  }


  @Override
  public void bindData(final @Nullable Object data) throws Exception {
    final List<ProjectStatsEnvelope.ReferrerStats> referrerStats = requireNonNull((List<ProjectStatsEnvelope.ReferrerStats>) data);
    this.viewModel.inputs.referrerStatsInput(referrerStats);
  }
}