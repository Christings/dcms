var PubSub = {
    subscribe: function(event, callback) {
        var events = this._events || (this._events = {})
        this._events[event] || (this._events[event] = []).push(callback)
    },
    publish: function() {
        var args = Array.prototype.slice.call(arguments, 0)
        var event = args.shift()
         
        var list, events
        if ( !(events = this._events) ) return
        if ( !(list = this._events[event]) ) return
         
        for (var i=0; i<list.length; i++) {
            list[i].apply(this, args)
        }
        return this
    }
}

export default PubSub;