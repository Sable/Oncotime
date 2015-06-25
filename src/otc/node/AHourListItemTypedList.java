/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class AHourListItemTypedList extends PTypedList
{
    private TTHour _tHour_;
    private PTypedList _typedList_;

    public AHourListItemTypedList()
    {
        // Constructor
    }

    public AHourListItemTypedList(
        @SuppressWarnings("hiding") TTHour _tHour_,
        @SuppressWarnings("hiding") PTypedList _typedList_)
    {
        // Constructor
        setTHour(_tHour_);

        setTypedList(_typedList_);

    }

    @Override
    public Object clone()
    {
        return new AHourListItemTypedList(
            cloneNode(this._tHour_),
            cloneNode(this._typedList_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAHourListItemTypedList(this);
    }

    public TTHour getTHour()
    {
        return this._tHour_;
    }

    public void setTHour(TTHour node)
    {
        if(this._tHour_ != null)
        {
            this._tHour_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._tHour_ = node;
    }

    public PTypedList getTypedList()
    {
        return this._typedList_;
    }

    public void setTypedList(PTypedList node)
    {
        if(this._typedList_ != null)
        {
            this._typedList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._typedList_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._tHour_)
            + toString(this._typedList_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._tHour_ == child)
        {
            this._tHour_ = null;
            return;
        }

        if(this._typedList_ == child)
        {
            this._typedList_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._tHour_ == oldChild)
        {
            setTHour((TTHour) newChild);
            return;
        }

        if(this._typedList_ == oldChild)
        {
            setTypedList((PTypedList) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
