/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class TTDaysType extends Token
{
    public TTDaysType()
    {
        super.setText("Days");
    }

    public TTDaysType(int line, int pos)
    {
        super.setText("Days");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TTDaysType(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTTDaysType(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TTDaysType text.");
    }
}