/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class TTDoctor extends Token
{
    public TTDoctor()
    {
        super.setText("doctors are");
    }

    public TTDoctor(int line, int pos)
    {
        super.setText("doctors are");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TTDoctor(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTTDoctor(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TTDoctor text.");
    }
}
