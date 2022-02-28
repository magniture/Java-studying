
export default function Serialize (a) {
  // /    <summary>
  // /        This method is internal.  Use serialize() instead.
  // /    </summary>
  // /    <param name="a" type="Map">A map of key/value pairs to serialize into a string.</param>'
  // /    <returns type="String" />
  // /    <private />

  var s = []

  function add (key, value) {
    s[s.length] = encodeURIComponent(key) + '=' + encodeURIComponent(value)
    // s[ s.length ] = (key) + '=' + (value);
  }

  // Serialize the key/values
  for (var j in a) {
    add(j, a[j])
  }

  // Return the resulting serialization
  return s.join('&').replace(/%20/g, '+')
}
