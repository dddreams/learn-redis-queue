# 基于 redis 的发布订阅模式的消息队列

其中包含了 websocket 的案例和 redis 调用 lua 脚本的案例，实现了计算最大值的脚本；

## 业务场景

利用消息队列来解决 websocket 的集群问题；

利用redis 调用 lua 脚本，保证原子操作；
