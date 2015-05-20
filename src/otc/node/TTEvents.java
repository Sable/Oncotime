/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class TTEvents extends Token
{
    public TTEvents()
    {
        super.setText("events are");
    }

    public TTEvents(int line, int pos)
    {
        super.setText("events are");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TTEvents(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTTEvents(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TTEvents text.");
    }
}
