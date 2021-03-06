/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class AEventType extends PType
{
    private TTEventType _tEventType_;

    public AEventType()
    {
        // Constructor
    }

    public AEventType(
        @SuppressWarnings("hiding") TTEventType _tEventType_)
    {
        // Constructor
        setTEventType(_tEventType_);

    }

    @Override
    public Object clone()
    {
        return new AEventType(
            cloneNode(this._tEventType_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAEventType(this);
    }

    public TTEventType getTEventType()
    {
        return this._tEventType_;
    }

    public void setTEventType(TTEventType node)
    {
        if(this._tEventType_ != null)
        {
            this._tEventType_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._tEventType_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._tEventType_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._tEventType_ == child)
        {
            this._tEventType_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._tEventType_ == oldChild)
        {
            setTEventType((TTEventType) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
