/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class TTDoctorFilter extends Token
{
    public TTDoctorFilter()
    {
        super.setText("doctors are");
    }

    public TTDoctorFilter(int line, int pos)
    {
        super.setText("doctors are");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TTDoctorFilter(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTTDoctorFilter(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TTDoctorFilter text.");
    }
}