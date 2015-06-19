/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class AMonthsListItemTypedList extends PTypedList
{
    private TTMonth _tMonth_;
    private PTypedList _typedList_;

    public AMonthsListItemTypedList()
    {
        // Constructor
    }

    public AMonthsListItemTypedList(
        @SuppressWarnings("hiding") TTMonth _tMonth_,
        @SuppressWarnings("hiding") PTypedList _typedList_)
    {
        // Constructor
        setTMonth(_tMonth_);

        setTypedList(_typedList_);

    }

    @Override
    public Object clone()
    {
        return new AMonthsListItemTypedList(
            cloneNode(this._tMonth_),
            cloneNode(this._typedList_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMonthsListItemTypedList(this);
    }

    public TTMonth getTMonth()
    {
        return this._tMonth_;
    }

    public void setTMonth(TTMonth node)
    {
        if(this._tMonth_ != null)
        {
            this._tMonth_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._tMonth_ = node;
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
            + toString(this._tMonth_)
            + toString(this._typedList_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._tMonth_ == child)
        {
            this._tMonth_ = null;
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
        if(this._tMonth_ == oldChild)
        {
            setTMonth((TTMonth) newChild);
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
