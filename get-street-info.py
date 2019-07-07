#!/usr/bin/env python3

import osmium as osm
import pandas as pd
import sys
import os

class RoadHandler(osm.SimpleHandler):
    def __init__(self):
        super(RoadHandler, self).__init__()
        self.streets = []

    def __extract_way(self, way):
        # res = {t.k: t.v for t in way.tags}
        # res["id"] = way.id
        res = {
            "id": way.id,
            "highway": way.tags["highway"],
            "oneway": True if "oneway" in way.tags and way.tags["oneway"] == 'yes' else False,
            "name": way.tags["name"] if "name" in way.tags else "",
            "lanes": way.tags["lanes"] if "lanes" in way.tags else None,
            "width": way.tags["width"] if "width" in way.tags else None,
            "surface": way.tags["surface"] if "surface" in way.tags else None
        }

        return res
    
    def way(self, w):
        if 'highway' in w.tags:
            try:
                self.streets.append(self.__extract_way(w))
            except osm.InvalidLocationError:
                print("WARNING: way %d incomplete. Ignoring." % w.id)

def main():
    h = RoadHandler()
    h.apply_file(sys.argv[1])

    output = os.path.basename(sys.argv[1])
    df = pd.DataFrame(h.streets)
    df.to_csv(output + ".csv", index=False)

if __name__ == '__main__':
    main()