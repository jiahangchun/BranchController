ViewResolver其实和HandlerMapping的初始化类似，都是在Tomcat经过第一次请求时进行initStrategies(),至于ViewResolver的初始化却是在Dispatcher中进行的。
Dispatcher.doDispatch() 在这个方法里面其实已经完成了业务逻辑的调用，同时也将进行ViewResolver相关处理。->
Dispatcher.processDispatchResult ->
render(mv, request, response); 这里就是处理了View的地方 ->
this.localeResolver.resolveLocale(request) 配置语言问题 ->
View view=resolveViewName(viewName, mv.getModelInternal(), locale, request); ->

