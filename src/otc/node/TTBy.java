/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class TTBy extends Token
{
    public TTBy()
    {
        super.setText("by");
    }

    public TTBy(int line, int pos)
    {
        super.setText("by");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TTBy(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTTBy(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TTBy text.");
    }
}
