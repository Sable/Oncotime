/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class TTCount extends Token
{
    public TTCount()
    {
        super.setText("count");
    }

    public TTCount(int line, int pos)
    {
        super.setText("count");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TTCount(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTTCount(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TTCount text.");
    }
}
