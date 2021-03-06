/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class APrintTableitemComputation extends PComputation
{
    private TTIdentifier _variable_;
    private TTIdentifier _iterator_;

    public APrintTableitemComputation()
    {
        // Constructor
    }

    public APrintTableitemComputation(
        @SuppressWarnings("hiding") TTIdentifier _variable_,
        @SuppressWarnings("hiding") TTIdentifier _iterator_)
    {
        // Constructor
        setVariable(_variable_);

        setIterator(_iterator_);

    }

    @Override
    public Object clone()
    {
        return new APrintTableitemComputation(
            cloneNode(this._variable_),
            cloneNode(this._iterator_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPrintTableitemComputation(this);
    }

    public TTIdentifier getVariable()
    {
        return this._variable_;
    }

    public void setVariable(TTIdentifier node)
    {
        if(this._variable_ != null)
        {
            this._variable_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._variable_ = node;
    }

    public TTIdentifier getIterator()
    {
        return this._iterator_;
    }

    public void setIterator(TTIdentifier node)
    {
        if(this._iterator_ != null)
        {
            this._iterator_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._iterator_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._variable_)
            + toString(this._iterator_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._variable_ == child)
        {
            this._variable_ = null;
            return;
        }

        if(this._iterator_ == child)
        {
            this._iterator_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._variable_ == oldChild)
        {
            setVariable((TTIdentifier) newChild);
            return;
        }

        if(this._iterator_ == oldChild)
        {
            setIterator((TTIdentifier) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
