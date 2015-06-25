/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class APostalcodeListItemTypedList extends PTypedList
{
    private TTPostalcode _tPostalcode_;
    private PTypedList _typedList_;

    public APostalcodeListItemTypedList()
    {
        // Constructor
    }

    public APostalcodeListItemTypedList(
        @SuppressWarnings("hiding") TTPostalcode _tPostalcode_,
        @SuppressWarnings("hiding") PTypedList _typedList_)
    {
        // Constructor
        setTPostalcode(_tPostalcode_);

        setTypedList(_typedList_);

    }

    @Override
    public Object clone()
    {
        return new APostalcodeListItemTypedList(
            cloneNode(this._tPostalcode_),
            cloneNode(this._typedList_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPostalcodeListItemTypedList(this);
    }

    public TTPostalcode getTPostalcode()
    {
        return this._tPostalcode_;
    }

    public void setTPostalcode(TTPostalcode node)
    {
        if(this._tPostalcode_ != null)
        {
            this._tPostalcode_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._tPostalcode_ = node;
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
            + toString(this._tPostalcode_)
            + toString(this._typedList_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._tPostalcode_ == child)
        {
            this._tPostalcode_ = null;
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
        if(this._tPostalcode_ == oldChild)
        {
            setTPostalcode((TTPostalcode) newChild);
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
