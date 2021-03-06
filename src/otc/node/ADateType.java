/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class ADateType extends PType
{
    private TTDateType _tDateType_;

    public ADateType()
    {
        // Constructor
    }

    public ADateType(
        @SuppressWarnings("hiding") TTDateType _tDateType_)
    {
        // Constructor
        setTDateType(_tDateType_);

    }

    @Override
    public Object clone()
    {
        return new ADateType(
            cloneNode(this._tDateType_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseADateType(this);
    }

    public TTDateType getTDateType()
    {
        return this._tDateType_;
    }

    public void setTDateType(TTDateType node)
    {
        if(this._tDateType_ != null)
        {
            this._tDateType_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._tDateType_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._tDateType_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._tDateType_ == child)
        {
            this._tDateType_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._tDateType_ == oldChild)
        {
            setTDateType((TTDateType) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
