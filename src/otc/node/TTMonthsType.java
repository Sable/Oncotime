/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class TTMonthsType extends Token
{
    public TTMonthsType()
    {
        super.setText("Months");
    }

    public TTMonthsType(int line, int pos)
    {
        super.setText("Months");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TTMonthsType(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTTMonthsType(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TTMonthsType text.");
    }
}
