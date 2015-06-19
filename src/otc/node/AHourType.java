/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class AHourType extends PType
{
    private TTHoursType _tHoursType_;

    public AHourType()
    {
        // Constructor
    }

    public AHourType(
        @SuppressWarnings("hiding") TTHoursType _tHoursType_)
    {
        // Constructor
        setTHoursType(_tHoursType_);

    }

    @Override
    public Object clone()
    {
        return new AHourType(
            cloneNode(this._tHoursType_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAHourType(this);
    }

    public TTHoursType getTHoursType()
    {
        return this._tHoursType_;
    }

    public void setTHoursType(TTHoursType node)
    {
        if(this._tHoursType_ != null)
        {
            this._tHoursType_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._tHoursType_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._tHoursType_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._tHoursType_ == child)
        {
            this._tHoursType_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._tHoursType_ == oldChild)
        {
            setTHoursType((TTHoursType) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}