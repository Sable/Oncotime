/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class TTTimeline extends Token
{
    public TTTimeline()
    {
        super.setText("timeline");
    }

    public TTTimeline(int line, int pos)
    {
        super.setText("timeline");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TTTimeline(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTTTimeline(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TTTimeline text.");
    }
}
