package com.kickstarter.viewmodels;

import android.support.annotation.NonNull;

import com.kickstarter.libs.ActivityViewModel;
import com.kickstarter.libs.Environment;
import com.kickstarter.libs.ReferrerType;
import com.kickstarter.services.apiresponses.ProjectStatsEnvelope;
import com.kickstarter.ui.viewholders.CreatorDashboardReferrerBreakDownViewHolder;

import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

public interface CreatorDashboardReferrerBreakdownHolderViewModel {

  interface Inputs {
    void referrerStatsInput(List<ProjectStatsEnvelope.ReferrerStats> referrerStats);
  }
  interface Outputs {
    Observable<Double> customReferrerPercent();
    Observable<Double> externalReferrerPercent();
    Observable<Double> internalReferrerPercent();
    Observable<Double> unknownReferrerPercent();
  }

  final class ViewModel extends ActivityViewModel<CreatorDashboardReferrerBreakDownViewHolder> implements Inputs, Outputs {

    public ViewModel(final @NonNull Environment environment) {
      super(environment);

      final Observable<List<ProjectStatsEnvelope.ReferrerStats>> referrerStats = this.referrerStatsInput;

      final Observable<List<ProjectStatsEnvelope.ReferrerStats>> internalReferrers = referrerStats
        .flatMap(rs ->
          Observable.from(rs).filter(r -> r.referrerType() == ReferrerType.INTERNAL).toList()
        );

      final Observable<List<ProjectStatsEnvelope.ReferrerStats>> externalReferrers = referrerStats
        .flatMap(rs ->
          Observable.from(rs).filter(r -> r.referrerType() == ReferrerType.EXTERNAL).toList()
        );

      final Observable<List<ProjectStatsEnvelope.ReferrerStats>> customReferrers = referrerStats
        .flatMap(rs ->
          Observable.from(rs).filter(r -> r.referrerType() == ReferrerType.CUSTOM).toList()
        );

      final Observable<List<ProjectStatsEnvelope.ReferrerStats>> unknownReferrers = referrerStats
        .flatMap(rs ->
          Observable.from(rs).filter(r -> r.referrerType() == null).toList()
        );

      this.externalReferrerPercent = externalReferrers
        .flatMap(rs ->
          Observable.from(rs)
            .reduce(0d, (accum, stat) -> accum + stat.percentageOfDollars())
        );

      this.internalReferrerPercent = internalReferrers
        .flatMap(rs ->
          Observable.from(rs)
            .reduce(0d, (accum, stat) -> accum + stat.percentageOfDollars())
        );

      this.customReferrerPercent = customReferrers
        .flatMap(rs ->
          Observable.from(rs)
            .reduce(0d, (accum, stat) -> accum + stat.percentageOfDollars())
        );

      this.unknownReferrerPercent = unknownReferrers
        .flatMap(rs ->
          Observable.from(rs)
            .reduce(0d, (accum, stat) -> accum + stat.percentageOfDollars())
        );
    }

    public final Inputs inputs = this;
    public final Outputs outputs = this;

    private final PublishSubject<List<ProjectStatsEnvelope.ReferrerStats>> referrerStatsInput = PublishSubject.create();

    private final Observable<Double> customReferrerPercent;
    private final Observable<Double> externalReferrerPercent;
    private final Observable<Double> internalReferrerPercent;
    private final Observable<Double> unknownReferrerPercent;

    @Override
    public void referrerStatsInput(final @NonNull List<ProjectStatsEnvelope.ReferrerStats> referrerStats) {
      this.referrerStatsInput.onNext(referrerStats);
    }

    @Override
    public @NonNull Observable<Double> customReferrerPercent() {
      return this.customReferrerPercent;
    }
    @Override
    public @NonNull Observable<Double> externalReferrerPercent() {
      return this.externalReferrerPercent;
    }
    @Override
    public @NonNull Observable<Double> internalReferrerPercent() {
      return this.internalReferrerPercent;
    }
    @Override
    public @NonNull Observable<Double> unknownReferrerPercent() {
      return this.unknownReferrerPercent;
    }
  }
}
