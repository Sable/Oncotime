/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class TTList extends Token
{
    public TTList()
    {
        super.setText("list");
    }

    public TTList(int line, int pos)
    {
        super.setText("list");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TTList(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTTList(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TTList text.");
    }
}
