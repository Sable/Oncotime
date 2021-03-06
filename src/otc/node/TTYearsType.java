/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class TTYearsType extends Token
{
    public TTYearsType()
    {
        super.setText("Years");
    }

    public TTYearsType(int line, int pos)
    {
        super.setText("Years");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TTYearsType(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTTYearsType(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TTYearsType text.");
    }
}
