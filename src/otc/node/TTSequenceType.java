/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class TTSequenceType extends Token
{
    public TTSequenceType()
    {
        super.setText("Sequence");
    }

    public TTSequenceType(int line, int pos)
    {
        super.setText("Sequence");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TTSequenceType(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTTSequenceType(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TTSequenceType text.");
    }
}