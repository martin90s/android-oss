package com.kickstarter.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.LinearLayout;

public class ReferrerBreakdownView extends View {

  public Canvas canvas;
  private Double customStartAngle;
  private Double customSweepAngle;
  private Double externalStartAngle;
  private Double externalSweepAngle;
  private Double internalStartAngle;
  private Double internalSweepAngle;
  private Double unknownStartAngle;
  private Double unknownSweepAngle;
  private RectF rectF;

  public ReferrerBreakdownView(Context context) {
    super(context);
    setWillNotDraw(false);
  }

  public void setCustomAngleAndColor(Double startAngle, Double sweepAngle) {
    this.customStartAngle = startAngle;
    this.customSweepAngle = sweepAngle;
  }

  public void setExternalAngleAndColor(Double startAngle, Double sweepAngle) {
    this.externalStartAngle = startAngle;
    this.externalSweepAngle = sweepAngle;
  }

  public void setInternalAngleAndColor(Double startAngle, Double sweepAngle) {
    this.internalStartAngle = startAngle;
    this.internalSweepAngle = sweepAngle;
  }

  public void setUnknownAngleAndColor(Double startAngle, Double sweepAngle) {
    this.unknownStartAngle = startAngle;
    this.unknownSweepAngle = sweepAngle;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    this.canvas = canvas;
    final Paint p = new Paint();
    p.setAntiAlias(true);
    p.setDither(true);
    p.setStyle(Paint.Style.FILL);
    p.setColor(Color.BLUE);
    //bottom left right top
    this.rectF = new RectF(0, 0, 400, 400);
    this.canvas.drawArc(this.rectF, this.customStartAngle.floatValue(), this.customSweepAngle.floatValue(), true, p);
    p.setColor(Color.GRAY);
    this.canvas.drawArc(this.rectF, this.externalStartAngle.floatValue(), this.externalSweepAngle.floatValue(), true, p);
    p.setColor(Color.BLACK);
    this.canvas.drawArc(this.rectF, this.internalStartAngle.floatValue(), this.internalSweepAngle.floatValue(), true, p);
    p.setColor(Color.DKGRAY);
    this.canvas.drawArc(this.rectF, this.unknownStartAngle.floatValue(), this.unknownSweepAngle.floatValue(), true, p);
    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
    params.height = 400;
    setLayoutParams(params);
  }
}
