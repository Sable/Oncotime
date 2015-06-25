/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class TRSquare extends Token
{
    public TRSquare()
    {
        super.setText("]");
    }

    public TRSquare(int line, int pos)
    {
        super.setText("]");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TRSquare(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTRSquare(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TRSquare text.");
    }
}